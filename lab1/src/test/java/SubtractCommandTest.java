import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.commands.Subtract;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SubtractCommandTest {

    @Test
    void subtractNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        stack.push(2.0);
        try {
            var SubtractCommand = new Subtract(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            SubtractCommand.execute(ctx);
            assertEquals(6.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        try {
            var addCommand = new Subtract(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(6.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        try {
            var addCommand = new Subtract(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(-14.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void AddNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        try {
            var SubtractCommand = new Subtract(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            SubtractCommand.execute(ctx);
            assertEquals(-107.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.75);
        stack.push(1.25);
        try {
            var addCommand = new Subtract(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(0.5, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void subtractNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        stack.push(-1.75);
        try {
            var subtractCommand = new Subtract(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            subtractCommand.execute(ctx);
            assertEquals(0.5, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void EmptyStack() throws CalcException {
        var stack = new ArrayDeque<Double>();
        var subtractCommand = new Subtract(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> subtractCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

    @Test
    void NotEnoughStack() throws CalcException {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        var subtractCommand = new Subtract(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> subtractCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }


}
