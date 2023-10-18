package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;

public class CallerTest {
    @Test
    @DisplayName("Test to call function from singly nested class")
    void testFromSinglyNestedClass() {
        class Temp {
            CallingInfo callingInfo;
            void test() {
                callingInfo = Caller.callingInfo();
            }
        }
        Temp temp = new Temp();
        temp.test();

        CallingInfo actualValue = temp.callingInfo;
        CallingInfo expectedValue = new CallingInfo("edu.hw2.task4.CallerTest$1Temp", "test");

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Test to call function from double nested class")
    void testFromDoubleNestedClass() {
        class Temp {
            public static class Nested {
                CallingInfo callingInfo;
                public Nested() {
                    test();
                }
                void test() {
                    callingInfo = Caller.callingInfo();
                }
            }
        }

        CallingInfo actualValue = new Temp.Nested().callingInfo;
        CallingInfo expectedValue = new CallingInfo("edu.hw2.task4.CallerTest$2Temp$Nested", "test");

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
