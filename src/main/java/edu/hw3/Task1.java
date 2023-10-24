package edu.hw3;

public final class Task1 {
    private static final int ASCII_OFFSET_LEFT_BORDER_UPPER = 65;
    private static final int ASCII_OFFSET_RIGHT_BORDER_UPPER = 90;
    private static final int ASCII_OFFSET_LEFT_BORDER_LOWER = 97;
    private static final int ASCII_OFFSET_RIGHT_BORDER_LOWER = 122;

    private Task1() {
    }

    public static String atbash(String string) {
        StringBuilder atbash = new StringBuilder();

        for (char ch : string.toCharArray()) {
            if (!isLatinLetter(ch)) {
                atbash.append(ch);
                continue;
            }

            atbash.append(convertToMirrored(ch, isUpper(ch)));
        }

        return atbash.toString();
    }

    private static char convertToMirrored(char ch, boolean isUpper) {
        if (isUpper) {
            int absOffset = Math.abs(ch - ASCII_OFFSET_LEFT_BORDER_UPPER);
            return (char) (ASCII_OFFSET_RIGHT_BORDER_UPPER - absOffset);
        } else {
            int absOffset = Math.abs(ch - ASCII_OFFSET_LEFT_BORDER_LOWER);
            return (char) (ASCII_OFFSET_RIGHT_BORDER_LOWER - absOffset);
        }
    }

    private static boolean isLatinLetter(char ch) {
        return isUpper(ch) || isLower(ch);
    }

    private static boolean isUpper(char ch) {
        return ASCII_OFFSET_LEFT_BORDER_UPPER <= ch && ch <= ASCII_OFFSET_RIGHT_BORDER_UPPER;
    }

    private static boolean isLower(char ch) {
        return ASCII_OFFSET_LEFT_BORDER_LOWER <= ch && ch <= ASCII_OFFSET_RIGHT_BORDER_LOWER;
    }
}
