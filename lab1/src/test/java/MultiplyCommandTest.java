import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.commands.Multiply;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MultiplyCommandTest {
    @Test
    void multiplyNaturalNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        stack.push(2.0);
        try {
            var multiplyCommand = new Multiply(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            multiplyCommand.execute(ctx);
            assertEquals(16.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeNumbers() {
        var stack = new ArrayDeque<Double>();
        stack.push(-2.0);
        stack.push(-8.0);
        try {
            var multiplyCommand = new Multiply(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            multiplyCommand.execute(ctx);
            assertEquals(16.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeAndPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(7.0);
        try {
            var multiplyCommand = new Multiply(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            multiplyCommand.execute(ctx);
            assertEquals(-49.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeAndBigPositiveNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-7.0);
        stack.push(100.0);
        try {
            var addCommand = new Multiply(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            addCommand.execute(ctx);
            assertEquals(-700.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(1.75);
        stack.push(1.25);
        try {
            var mutiplyCommand = new Multiply(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            mutiplyCommand.execute(ctx);
            assertEquals(2.1875, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void multiplyNegativeRealNums() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        stack.push(-1.75);
        try {
            var multiplyCommand = new Multiply(new String[]{});
            var ctx = new MemoryContext(stack, new HashMap<>());
            multiplyCommand.execute(ctx);
            assertEquals(2.1875, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void EmptyStack() throws CalcException {
        var stack = new ArrayDeque<Double>();
        var multiplyCommand = new Multiply(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> multiplyCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

    @Test
    void NotEnoughStack() throws CalcException {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.25);
        var multiplyCommand = new Multiply(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> multiplyCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

}
