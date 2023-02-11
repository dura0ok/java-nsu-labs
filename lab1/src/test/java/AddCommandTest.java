import fit.nsu.labs.commands.Add;
import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
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
            addCommand.execute(new MemoryContext(stack, new HashMap<>()));
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
            addCommand.execute(new MemoryContext(stack, new HashMap<>()));
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
            addCommand.execute(new MemoryContext(stack, new HashMap<>()));
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
            addCommand.execute(new MemoryContext(stack, new HashMap<>()));
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
            addCommand.execute(new MemoryContext(stack, new HashMap<>()));
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
            addCommand.execute(new MemoryContext(stack, new HashMap<>()));
            assertEquals(0.5, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }


    @Test
    void EmptyStack() {
        var stack = new ArrayDeque<Double>();
        var addCommand = new Add(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> addCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

    @Test
    void NotEnoughStack() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        var addCommand = new Add(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> addCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }
}
