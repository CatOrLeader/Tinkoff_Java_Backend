package edu.hw1;

import org.apache.logging.log4j.LogManager;

public final class Task7 {
    private Task7() {
    }

    public static int rotateLeft(int n, int shift) {
        if (n <= 0 || shift <= 0) {
            return -1;
        }

        String bin = toBinary(n);
        int size = bin.length();
        int realShift = shift % size;

        StringBuilder shifted = new StringBuilder();
        for (int i = 0; i < realShift; i++) {
            shifted.append(bin.charAt(i));
        }

        StringBuilder remains = new StringBuilder();
        for (int i = realShift; i < size; i++) {
            remains.append(bin.charAt(i));
        }

        LogManager.getLogger().info(bin + " " + remains + " " + shifted);

        return toDec(remains + shifted.toString());
    }

    public static int rotateRight(int n, int shift) {
        if (n <= 0 || shift <= 0) {
            return -1;
        }

        String bin = toBinary(n);
        int size = bin.length();
        int realShift = shift % size;

        StringBuilder shifted = new StringBuilder();
        for (int i = 0; i < realShift; i++) {
            shifted.append(bin.charAt(size - i - 1));
        }
        shifted.reverse();

        StringBuilder remains = new StringBuilder();
        for (int i = 0; i < size - realShift; i++) {
            remains.append(bin.charAt(i));
        }

        return toDec(shifted.toString() + remains);
    }

    private static String toBinary(int n) {
        StringBuilder sb = new StringBuilder();
        int temp = n;

        while (temp > 0) {
            sb.append(temp % 2);
            temp /= 2;
        }

        return sb.reverse().toString();
    }

    private static int toDec(String bin) {
        int n = 0;
        int size = bin.length();

        for (int i = 0; i < size; i++) {
            n += (int) Math.pow(2, i) * Integer.parseInt(String.valueOf(bin.charAt(size - i - 1)));
        }

        return n;
    }
}
