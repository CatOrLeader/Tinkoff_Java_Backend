package edu.hw6;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.util.Map.entry;

public final class Task6 {
    private Task6() {
    }

    private static final String SEPARATOR = " ".repeat(8);
    private static final int PORT_OUTPUT_OFFSET = 11;
    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("checkstyle:MultipleStringLiterals") public static final Map<Integer, String> POSSIBLE_SERVICE =
        Map.ofEntries(
            entry(80, "HTTP"),
            entry(21, "FTP"),
            entry(25, "SMTP"),
            entry(443, "HTTPS"),
            entry(53, "DNS"),
            entry(3306, "MySQL DB"),
            entry(5432, "PostgreSQL DB"),
            entry(3389, "RDP"),
            entry(27017, "MongoDB DB"),
            entry(1551, "Oracle DB"),
            entry(49152, "Windows RPC"),
            entry(5353, "mDNS"),
            entry(5672, "AMQP"),
            entry(5355, "LLMNR"),
            entry(23, "Telnet"),
            entry(110, "POP3"),
            entry(143, "IMAP"),
            entry(67, "DHCP"),
            entry(68, "DHCP"),
            entry(123, "NTP"),
            entry(161, "SNMP"),
            entry(162, "SNMP"),
            entry(445, "SMB"),
            entry(548, "AFP")
        );

    private static final int MAX_PORT_NUMBER = 49151;
    private static final int TCP_BUSY = 1;
    private static final int UDP_BUSY = 2;

    public static int[] scanPorts() {
        int[] isBusy = new int[MAX_PORT_NUMBER];

        for (int i = 0; i < MAX_PORT_NUMBER; i++) {
            isBusy[i] = isPortBusy(i);
        }

        return isBusy;
    }

    public static String prettyOutput(int[] ports) {
        StringBuilder sb = new StringBuilder();

        sb.append("Protocol").append(SEPARATOR).append("Port").append(SEPARATOR).append("Service\n");
        for (int i = 0; i < ports.length; i++) {
            if (ports[i] == 0 || POSSIBLE_SERVICE.get(i) == null) {
                continue;
            }

            sb.append(prettyPortOutput(i, ports[i]));
        }

        return sb.toString();
    }

    private static String prettyPortOutput(int port, int isBusy) {
        return (isBusy == 1 ? "  TCP" : "  UDP")
               + " ".repeat(PORT_OUTPUT_OFFSET)
               + port
               + " ".repeat(PORT_OUTPUT_OFFSET + 1 - String.valueOf(port).length())
               + POSSIBLE_SERVICE.get(port)
               + "\n";
    }

    private static int isPortBusy(int port) {
        try (ServerSocket ignored1 = new ServerSocket(port)) {
            LOGGER.info("no tcp on " + port);
        } catch (Exception ignored) {
            return TCP_BUSY;
        }

        try (DatagramSocket ignored1 = new DatagramSocket(port)) {
            LOGGER.info("no udp on " + port);
        } catch (Exception ignored) {
            return UDP_BUSY;
        }

        return 0;
    }
}
