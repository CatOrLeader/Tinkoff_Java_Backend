package edu.hw8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Task1 {
    private Task1() {
    }

    public static final class Server {
        private static final Map<String, String> MAP = Map.of(
            "личности",
            "Не переходи на личности там, где их нет",
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
            "глупый",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
            "интеллект",
            "Чем ниже интеллект, тем громче оскорбления"
        );

        private final ExecutorService pool;
        private final ServerSocket serverSocket;

        public Server(int connectionThreshold, int port) throws IOException {
            this.pool = Executors.newFixedThreadPool(connectionThreshold);
            this.serverSocket = new ServerSocket(port);
        }

        public void start() throws IOException, ExecutionException, InterruptedException {
            while (true) {
                Socket client;
                try {
                    client = serverSocket.accept();
                } catch (SocketException ignored) {
                    return;
                }
                pool.execute(new MessageHandler(client));
            }
        }

        public void stop() throws IOException {
            pool.shutdown();
            serverSocket.close();
        }

        static final class MessageHandler extends Thread {
            private final Socket client;
            private final BufferedReader reader;
            private final BufferedWriter writer;

            MessageHandler(Socket client) throws IOException {
                this.client = client;
                this.reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                this.writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
            }

            @Override
            public void run() {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        String answer = MAP.getOrDefault(message, "Сервер не может ответить");
                        write(answer);
                    }

                    shutdown();
                } catch (IOException ignored) {
                }
            }

            private void shutdown() throws IOException {
                reader.close();
                writer.close();
                client.close();
            }

            private void write(String msg) throws IOException {
                writer.write(msg);
                writer.newLine();
                writer.flush();
            }
        }
    }

    public static final class Client {
        private final Socket socket;
        private final BufferedReader reader;
        private final BufferedWriter writer;

        public Client(String ip, int port) throws IOException {
            this.socket = new Socket(ip, port);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        }

        public String sendAndGet(String message) throws IOException {
            write(message);
            return reader.readLine();
        }

        public void disconnect() throws IOException {
            reader.close();
            writer.close();
            socket.close();
        }

        private void write(String msg) throws IOException {
            writer.write(msg);
            writer.newLine();
            writer.flush();
        }
    }
}
