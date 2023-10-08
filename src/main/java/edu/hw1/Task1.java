package edu.hw1;

import java.time.LocalTime;
import java.util.Scanner;

public final class Task1 {
    private final static Scanner SCANNER = new Scanner(System.in);

    private Task1() {
    }

    public static void main(String[] args) {
        minutesToSeconds(SCANNER.nextLine());
    }

    public static long minutesToSeconds(String input) {
        if (!input.matches("(\\d+):([0-5]\\d)")) {
            return -1;
        }

        String[] split = input.split(":");
        LocalTime time = LocalTime.of(
            0,
            Integer.parseInt(split[0]),
            Integer.parseInt(split[1])
        );

        return time.toSecondOfDay();
    }
}
