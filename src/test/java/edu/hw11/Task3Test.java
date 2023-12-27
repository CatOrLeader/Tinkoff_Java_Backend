package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Dynamically created Fibonacci numbers")
    void dynamicallyCreatedFibonacci()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ByteCodeAppender appender = new ByteCodeAppender() {
            @Override
            public @NotNull Size apply(
                @NotNull MethodVisitor methodVisitor,
                Implementation.@NotNull Context context,
                @NotNull MethodDescription methodDescription
            ) {
                Label start = new Label();
                methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
                methodVisitor.visitInsn(Opcodes.ICONST_2);
                methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, start);
                methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);

                methodVisitor.visitInsn(Opcodes.I2L);
                methodVisitor.visitInsn(Opcodes.LRETURN);
                methodVisitor.visitLabel(start);
                methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

                methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
                methodVisitor.visitInsn(Opcodes.ICONST_1);
                methodVisitor.visitInsn(Opcodes.ISUB);
                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "FibonacciCalculator",
                    "fib",
                    "(I)J",
                    false
                );

                methodVisitor.visitVarInsn(Opcodes.ILOAD, 0);
                methodVisitor.visitInsn(Opcodes.ICONST_2);
                methodVisitor.visitInsn(Opcodes.ISUB);
                methodVisitor.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "FibonacciCalculator",
                    "fib",
                    "(I)J",
                    false
                );

                methodVisitor.visitInsn(Opcodes.LADD);
                methodVisitor.visitInsn(Opcodes.LRETURN);

                return new Size(8, 0);
            }
        };

        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
            .name("FibonacciCalculator")
            .defineMethod("fib", long.class, Modifier.PUBLIC | Modifier.STATIC).withParameter(int.class, "n")
            .intercept(new Implementation.Simple(appender))
            .make().load(getClass().getClassLoader()).getLoaded();

        long actualValue = (long) dynamicType.getMethod("fib", int.class).invoke(null, 5);
        long expectedValue = 5;

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
