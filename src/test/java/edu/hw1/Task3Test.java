package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task3.isNestable;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Correct Input 1")
    void correctInput1() {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {0, 6};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Input 2")
    void correctInput2() {
        int[] arr1 = {3, 1};
        int[] arr2 = {4, 0};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Input 3")
    void correctInput3() {
        int[] arr1 = {9, 9, 8};
        int[] arr2 = {9, 8};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Input 4")
    void correctInput4() {
        int[] arr1 = {1, 2, 3, 4};
        int[] arr2 = {2, 3};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Correct Input w/ negative values")
    void correctInputNegValues() {
        int[] arr1 = {-923, -234, -12, -3};
        int[] arr2 = {-1000, -1};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = true;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Incorrect Input w/ negative values")
    void incorrectInputNegValues() {
        int[] arr1 = {-923, -234, -12, -1};
        int[] arr2 = {-900, -1};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Null first array")
    void nullFirstArray() {
        int[] arr1 = null;
        int[] arr2 = {9, 8};

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Null second array")
    void nullSecondArray() {
        int[] arr1 = {9, 9, 8};
        int[] arr2 = null;

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Null both arrays")
    void nullBothArrayS() {
        int[] arr1 = null;
        int[] arr2 = null;

        boolean actualValue = isNestable(arr1, arr2);
        boolean expectedValue = false;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
