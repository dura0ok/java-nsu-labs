import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Subtract;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SubtractCommandTest {

    @Test
    void subtractNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        stack.push(2.0);
        var addCommand = new Subtract(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(6.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        var addCommand = new Subtract(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(6.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        var addCommand = new Subtract(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-14.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void AddNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        var addCommand = new Subtract(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-107.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.75);
        stack.push(1.25);
        var addCommand = new Subtract(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(0.5, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        stack.push(-1.75);
        var addCommand = new Subtract(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(0.5, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }


}
