package edu.hw3;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class Task4 {
    private static final int MAX_VALUE = 4000;
    private static final List<RomanNumerals> NUMERALS_REVERSED = Arrays.stream(RomanNumerals.values())
        .toList().reversed();

    private Task4() {
    }

    @NotNull
    public static String convertToRoman(int value) {
        if (!(1 <= value && value <= MAX_VALUE)) {
            throw new IllegalArgumentException("incorrect value provided; possible range [1, 4000]");
        }
        int number = value;

        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (number > 0) {
            RomanNumerals currentSymbol = NUMERALS_REVERSED.get(index);
            int currentValue = currentSymbol.value;

            if (currentValue <= number) {
                builder.append(currentSymbol);
                number -= currentValue;
            } else {
                index++;
            }
        }

        return builder.toString();
    }

    private enum RomanNumerals {
        I(1), IV(5), V(5), IX(9),
        X(10), XL(40), L(50), XC(90),
        C(100), CD(400), D(500), M(1000);

        private final int value;

        RomanNumerals(int value) {
            this.value = value;
        }
    }
}
