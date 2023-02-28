import fit.nsu.labs.commands.Add;
import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.commands.Push;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PushCommandTest {
    @Test
    void pushEmptyError() throws CalcException {
        var stack = new ArrayDeque<Double>();
        var pushCommand = new Add(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> pushCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

    @Test
    void addNaturalNums() {
        try {
            var stack = new ArrayDeque<Double>();
            var ctx = new MemoryContext(stack, new HashMap<>());
            var pushCommand = new Push(new String[]{"10"});
            pushCommand.execute(ctx);
            assertEquals(10.0, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void pushDefined() {

        try {
            var stack = new ArrayDeque<Double>();
            var ctx = new MemoryContext(stack, new HashMap<>());
            ctx.defineNumber("a", 4.0);
            var pushCommand = new Push(new String[]{"a"});
            pushCommand.execute(ctx);
            assertEquals(4, ctx.popStack());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void tryToPushNotDefined() throws CalcException {
        var stack = new ArrayDeque<Double>();
        var pushCommand = new Push(new String[]{"a"});
        assertThrows(InvalidCommandArgument.class, () -> pushCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }
}
