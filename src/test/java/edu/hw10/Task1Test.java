package edu.hw10;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw10.Task1.Max;
import static edu.hw10.Task1.Min;
import static edu.hw10.Task1.NotNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task1Test {
    private enum CreationMethod {
        CONSTRUCTOR, METHOD
    }

    private static final class UncreatableObject {
        private UncreatableObject() {
        }
    }

    public record IncompatibleTypeClass(double value) {
    }

    private static final class NoArgsClass {
        final CreationMethod creationMethod;
        private final int TEMP;

        public NoArgsClass() {
            creationMethod = CreationMethod.CONSTRUCTOR;
            TEMP = 10;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            NoArgsClass that = (NoArgsClass) o;

            return TEMP == that.TEMP;
        }

        @Override
        public int hashCode() {
            return TEMP;
        }
    }

    public static final class TwoTypesClass {
        CreationMethod creationMethod;
        final int integer;
        final String string;

        public TwoTypesClass(int integer, String string) {
            creationMethod = CreationMethod.CONSTRUCTOR;
            this.integer = integer;
            this.string = string;
        }

        public static TwoTypesClass create(double temp) {
            return null;
        }

        public static TwoTypesClass create(int integer, String string) {
            TwoTypesClass newObj = new TwoTypesClass(integer, string);
            newObj.creationMethod = CreationMethod.METHOD;
            return newObj;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TwoTypesClass that = (TwoTypesClass) o;

            if (integer != that.integer) {
                return false;
            }
            return Objects.equals(string, that.string);
        }

        @Override
        public int hashCode() {
            int result = integer;
            result = 31 * result + (string != null ? string.hashCode() : 0);
            return result;
        }
    }

    public static final class TwoTypesClassWithAnnotations {
        static final int MIN_VALUE = 100;
        static final int MAX_VALUE = 200;

        final CreationMethod creationMethod;
        final int integer;
        final String string;

        public TwoTypesClassWithAnnotations(
            @Min(minValue = 100) @Max(maxValue = 200) int integer, @NotNull String string
        ) {
            creationMethod = CreationMethod.CONSTRUCTOR;
            this.integer = integer;
            this.string = string;
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TwoTypesClassWithAnnotations that = (TwoTypesClassWithAnnotations) o;

            if (integer != that.integer) {
                return false;
            }
            return Objects.equals(string, that.string);
        }

        @Override
        public int hashCode() {
            int result = integer;
            result = 31 * result + (string != null ? string.hashCode() : 0);
            return result;
        }
    }

    @Test
    @DisplayName("Try to create object with no constructors")
    void createObjectWithNoConstructors() {
        assertThatExceptionOfType(NoSuchMethodException.class)
            .isThrownBy(() -> Task1.RandomObjectGenerator.nextObject(UncreatableObject.class));
    }

    @Test
    @DisplayName("Try to create object with no methods")
    void createObjectWithNoMethods() {
        assertThatExceptionOfType(NoSuchMethodException.class)
            .isThrownBy(() -> Task1.RandomObjectGenerator.nextObject(UncreatableObject.class, "Create"));
    }

    @Test
    @DisplayName("Try to create object with incompatible type")
    void createObjectWithIncompatibleType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task1.RandomObjectGenerator.nextObject(IncompatibleTypeClass.class));
    }

    @Test
    @DisplayName("Create object with no arguments")
    void createObjectWithNoArguments_FromConstructor()
        throws NoSuchMethodException {
        NoArgsClass actualValue = (NoArgsClass) Task1.RandomObjectGenerator.nextObject(NoArgsClass.class);
        NoArgsClass expectedValue = new NoArgsClass();

        assertThat(actualValue).isEqualTo(expectedValue);
        assertThat(actualValue.creationMethod).isEqualTo(CreationMethod.CONSTRUCTOR);
    }

    @Test
    @DisplayName("Null can be created if it is possible")
    void nullCanBeCreated() throws NoSuchMethodException {
        final int maxCount = 100;

        boolean isNullCanBeCreated = isNullCanBeCreatedFromNIterations(maxCount);

        assertThat(isNullCanBeCreated).isTrue();
    }

    @Test
    @DisplayName("All the annotations are satisfied")
    void allAnnotationsAreSatisfied() throws NoSuchMethodException {
        final int maxCount = 100;

        boolean areBoundsSatisfiedForEveryone = areBoundsSatisfiedFromNIterations(maxCount);

        assertThat(areBoundsSatisfiedForEveryone).isTrue();
    }

    @Test
    @DisplayName("Create object with two arguments with two different types: int, string")
    void createObjectWithTwoArgumentTypes_FromConstructor()
        throws NoSuchMethodException {
        TwoTypesClass actualValue = (TwoTypesClass) Task1.RandomObjectGenerator.nextObject(TwoTypesClass.class);

        assertThat(actualValue).isInstanceOf(TwoTypesClass.class);
        assertThat(actualValue.creationMethod).isEqualTo(CreationMethod.CONSTRUCTOR);
    }

    @Test
    @DisplayName("Create object with two arguments with two different types: int, string")
    void createObjectWithTwoArgumentTypes_FromMethod() throws NoSuchMethodException {
        TwoTypesClass actualValue =
            (TwoTypesClass) Task1.RandomObjectGenerator.nextObject(TwoTypesClass.class, "create");

        assertThat(actualValue).isInstanceOf(TwoTypesClass.class);
        assertThat(actualValue.creationMethod).isEqualTo(CreationMethod.METHOD);
    }

    private static boolean isNullCanBeCreatedFromNIterations(int N) throws NoSuchMethodException {
        for (int i = 0; i < N; i++) {
            if (isNullCreated()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNullCreated() throws NoSuchMethodException {
        return ((TwoTypesClass) Task1.RandomObjectGenerator.nextObject(TwoTypesClass.class)).string == null;
    }

    private static boolean areBoundsSatisfiedFromNIterations(int N) throws NoSuchMethodException {
        for (int i = 0; i < N; i++) {
            if (!areBoundsSatisfied()) {
                return false;
            }
        }
        return true;
    }

    private static boolean areBoundsSatisfied() throws NoSuchMethodException {
        var object =
            (TwoTypesClassWithAnnotations) Task1.RandomObjectGenerator.nextObject(TwoTypesClassWithAnnotations.class);
        return (TwoTypesClassWithAnnotations.MIN_VALUE <= object.integer
                && object.integer <= TwoTypesClassWithAnnotations.MAX_VALUE)
               && object.string != null;
    }
}
