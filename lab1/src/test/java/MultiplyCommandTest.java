import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Multiply;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MultiplyCommandTest {
    @Test
    void multiplyNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        stack.push(2.0);
        var addCommand = new Multiply(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(16.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        var addCommand = new Multiply(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(16.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        var addCommand = new Multiply(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-49.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        var addCommand = new Multiply(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-700.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.75);
        stack.push(1.25);
        var addCommand = new Multiply(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(2.1875, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        stack.push(-1.75);
        var addCommand = new Multiply(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(2.1875, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }
}
