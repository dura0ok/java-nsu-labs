import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Add;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddCommandTest {

    @Test
    void addNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(2.0);
        stack.push(8.0);
        var addCommand = new Add(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(10.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void addNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        var addCommand = new Add(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(-10.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void AddNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        var addCommand = new Add(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void AddNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        var addCommand = new Add(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(93.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void addRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.25);
        stack.push(1.75);
        var addCommand = new Add(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(3.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void addNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        stack.push(1.75);
        var addCommand = new Add(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(0.5, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }


}
