import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Divide;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DivideCommandTest {
    @Test
    void divideNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        stack.push(2.0);
        var divideCommand = new Divide(new String[]{});
        try {
            divideCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(4.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        var addCommand = new Divide(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(0.25, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        var addCommand = new Divide(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-1.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        var addCommand = new Divide(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-0.07, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.75);
        stack.push(1.25);
        var addCommand = new Divide(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(1.4, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.5);
        stack.push(-0.5);
        var addCommand = new Divide(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(5, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }
}
