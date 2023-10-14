package edu.hw1;

public final class Task3 {
    private Task3() {
    }

    public static boolean isNestable(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null) {
            return false;
        }

        return min(arr1) > min(arr2) && max(arr1) < max(arr2);
    }

    private static int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private static int min(int[] arr) {
        int min = Integer.MAX_VALUE;
        for (int num : arr) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }
}
