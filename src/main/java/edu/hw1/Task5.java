package edu.hw1;

public final class Task5 {
    private Task5() {
    }

    /**
     * Method checks if the number or its descendants (pairwise summation of all the digits in the number)
     * are palindromes. Additionally, if there is an odd length of the string, one insignificant zero
     * added to the beginning.
     *
     * @param num number to be checked. Parent of descendants.
     * @return if it is (or its descendants are palindromes) - true; Otherwise - false
     */
    public static boolean isPalindromeDescendant(long num) {
        String stringNum = String.valueOf(num);

        if (stringNum.charAt(0) == '-') {
            stringNum = stringNum.substring(1);
        }
        int size = stringNum.length();

        while (size > 1) {
            if (isPalindrome(stringNum)) {
                return true;
            }
            stringNum = reduceString(stringNum);
            size = stringNum.length();
        }

        return false;
    }

    private static String reduceString(String str) {
        StringBuilder sb;
        int size = str.length();
        if (size % 2 != 0) {
            sb = new StringBuilder("0" + str);
        } else {
            sb = new StringBuilder(str);
        }

        StringBuilder reduced = new StringBuilder();
        for (int i = 0; i < size; i += 2) {
            reduced.append(
                Integer.parseInt(String.valueOf(sb.charAt(i))) + Integer.parseInt(String.valueOf(sb.charAt(i + 1)))
            );
        }

        return reduced.toString();
    }

    private static boolean isPalindrome(String str) {
        int size = str.length();
        int mid = size / 2;

        for (int i = 0; i < mid; i++) {
            if (str.charAt(i) != str.charAt(size - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
