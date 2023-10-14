package edu.hw1;

public final class Task6 {
    private static final int PREFIX_SIZE = 10;
    private static final int CAPRECAR_CONST = 6174;
    private static final int MIN_BORDER = 1000;
    private static final int MAX_BORDER = 10000;

    private Task6() {
    }

    public static int countK(int num) {
        if (!(MIN_BORDER < num && num < MAX_BORDER)) {
            return -1;
        }

        if (allDigitsAreSame(num)) {
            return -1;
        }

        return recCountK(num, 0);
    }

    private static int recCountK(int num, int steps) {
        if (num == CAPRECAR_CONST) {
            return steps;
        }

        int incrSteps = steps + 1;
        return recCountK(
            descendingOrder(num) - ascendingOrder(num),
            incrSteps
        );
    }

    private static int ascendingOrder(int num) {
        int[] prefix = getPrefixArray(num);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PREFIX_SIZE; i++) {
            sb.append(String.valueOf(i).repeat(prefix[i]));
        }

        return Integer.parseInt(sb.toString());
    }

    private static int descendingOrder(int num) {
        int[] prefix = getPrefixArray(num);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PREFIX_SIZE; i++) {
            sb.append(String.valueOf(i).repeat(prefix[i]));
        }

        return Integer.parseInt(sb.reverse().toString());
    }

    private static boolean allDigitsAreSame(int num) {
        int[] prefix = getPrefixArray(num);

        int count = 0;
        for (int cnt : prefix) {
            if (cnt != 0) {
                count++;
            }
        }

        return count <= 1;
    }

    private static int[] getPrefixArray(int num) {
        int[] prefix = new int[PREFIX_SIZE];

        for (char ch : String.valueOf(num).toCharArray()) {
            prefix[Integer.parseInt(String.valueOf(ch))]++;
        }

        return prefix;
    }
}
