package edu.project1;

public final class Application {
    private Application() {
    }

    public static void main(String[] args) {
        new HangmanGame(System.in).run();
    }
}
