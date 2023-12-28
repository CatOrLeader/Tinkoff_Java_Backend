package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    public static final class ArithmeticUtils {
        private ArithmeticUtils() {
        }

        public static int sum(int a, int b) {
            return a + b;
        }
    }

    @Test
    @DisplayName("From sum to mul")
    void fromSumToMul() {
        final int a = 3;
        final int b = 1;
        assertThat(ArithmeticUtils.sum(a, b)).isEqualTo(a + b);

        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum").and(takesArguments(2))).intercept(FixedValue.value(a * b))
            .make()
            .load(
                getClass().getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );

        int actualValue = ArithmeticUtils.sum(a, b);
        int expectedValue = a * b;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
