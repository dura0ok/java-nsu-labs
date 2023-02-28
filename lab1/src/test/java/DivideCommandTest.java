import fit.nsu.labs.commands.Divide;
import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DivideCommandTest {
    @Test
    void divideNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        stack.push(2.0);
        try {
            var divideCommand = new Divide(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            divideCommand.execute(ctx);
            assertEquals(4.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        try {
            var addCommand = new Divide(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(0.25, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        try {
            var divideCommand = new Divide(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            divideCommand.execute(ctx);
            assertEquals(-1.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        try {
            var divideCommand = new Divide(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            divideCommand.execute(ctx);
            assertEquals(-0.07, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.75);
        stack.push(1.25);
        try {
            var addCommand = new Divide(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(1.4, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void divideNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.5);
        stack.push(-0.5);
        try {
            var addCommand = new Divide(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(5, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void EmptyStack() throws CalcException {
        var stack = new ArrayDeque<Double>();
        var divideCommand = new Divide(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> divideCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

    @Test
    void NotEnoughStack() throws CalcException {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        var divideCommand = new Divide(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> divideCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }
}
