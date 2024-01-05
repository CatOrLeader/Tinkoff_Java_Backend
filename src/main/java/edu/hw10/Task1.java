package edu.hw10;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.jetbrains.annotations.Nullable;

public final class Task1 {
    private Task1() {
    }

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE,
        ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface NotNull {
        String value() default "";

        Class<? extends Exception> exception() default Exception.class;
    }

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Min {
        double minValue();

        Class<? extends Exception> exception() default Exception.class;
    }

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Max {
        double maxValue();

        Class<? extends Exception> exception() default Exception.class;
    }

    static final class RandomObjectGenerator {

        private static final Map<Class<?>, RandomGenerator<?>> GENERATORS =
            Map.of(
                String.class, new RandomStringGenerator(),
                int.class, new RandomIntGenerator()
            );

        public static Object nextObject(Class<?> obj)
            throws NoSuchMethodException {
            Constructor<?>[] constructors = obj.getConstructors();
            if (constructors.length == 0) {
                throw new NoSuchMethodException("No constructors exist");
            }

            for (var constructor : constructors) {
                try {
                    return createFromConstructor(constructor);
                } catch (Exception ignored) {
                }
            }

            throw new IllegalArgumentException();
        }

        public static Object nextObject(Class<?> obj, String methodName) throws NoSuchMethodException {
            List<Method> creationMethods =
                Arrays.stream(obj.getMethods()).filter(method -> method.getName().equals(methodName)).toList();
            if (creationMethods.isEmpty()) {
                throw new NoSuchMethodException();
            }
            for (var method : creationMethods) {
                try {
                    if (method.getName().equals(methodName)) {
                        return createFromMethod(method);
                    }
                } catch (Exception ignored) {
                }
            }

            throw new IllegalArgumentException();
        }

        private static Object createFromConstructor(Constructor<?> constructor)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
            return constructor.newInstance(getRandomlyGeneratedParameters(constructor.getParameters()).toArray());
        }

        private static Object createFromMethod(Method creationMethod)
            throws InvocationTargetException, IllegalAccessException {
            return creationMethod.invoke(
                null,
                getRandomlyGeneratedParameters(creationMethod.getParameters()).toArray()
            );
        }

        private static List<Object> getRandomlyGeneratedParameters(Parameter... givenParameters) {
            List<Object> parameters = new ArrayList<>();
            for (Parameter parameter : givenParameters) {
                Class<?> current = parameter.getType();

                if (!(GENERATORS.containsKey(current))) {
                    throw new IllegalArgumentException(
                        "No such generator for parameter of this type: " + parameter.getClass());
                }
                parameters.add(GENERATORS.get(current).next(parameter.getAnnotations()));
            }
            return parameters;
        }
    }

    @FunctionalInterface
    private interface RandomGenerator<T> {
        @Nullable T next(Annotation[] annotations);

        default boolean isNotNull(Annotation[] annotations) {
            return Arrays.stream(annotations)
                .map(Annotation::annotationType)
                .toList().contains(NotNull.class);
        }

        default Borders getBorders(Annotation[] annotations) {
            var list =
                Arrays.stream(annotations).toList();
            var comparableList = Arrays.stream(annotations).map(Annotation::annotationType).toList();

            return new Borders(
                comparableList.contains(Min.class)
                    ? ((Min) list.get(comparableList.indexOf(Min.class))).minValue()
                    : Integer.MIN_VALUE,
                comparableList.contains(Max.class)
                    ? ((Max) list.get(comparableList.indexOf(Max.class))).maxValue()
                    : Integer.MAX_VALUE
            );
        }

        record Borders(double min, double max) {
        }
    }

    private static final class RandomIntGenerator implements RandomGenerator<Integer> {
        private RandomIntGenerator() {
        }

        private final Random random = new Random();

        @Override
        public @NotNull Integer next(Annotation[] annotations) {
            Borders borders = getBorders(annotations);

            return random.nextInt((int) borders.min, (int) borders.max);
        }
    }

    private static final class RandomStringGenerator implements RandomGenerator<String> {
        private RandomStringGenerator() {
        }

        private static final int LOWER_BOUND = 1;
        private static final int UPPER_BOUND = 32;
        private static final int CODEPOINTS_UPPER_BOUND = 128;

        private final Random random = new Random();

        @Override
        public @Nullable String next(Annotation[] annotations) {
            if (!isNotNull(annotations) && random.nextInt(CODEPOINTS_UPPER_BOUND) < UPPER_BOUND) {
                return null;
            }

            StringBuilder builder = new StringBuilder();
            int size = random.nextInt(LOWER_BOUND, UPPER_BOUND + 1);
            for (int i = 0; i < size; i++) {
                builder.append((char) random.nextInt(CODEPOINTS_UPPER_BOUND));
            }
            return builder.toString();
        }
    }
}
