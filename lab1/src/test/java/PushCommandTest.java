import fit.nsu.labs.commands.Add;
import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.commands.Push;
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
    void pushEmptyError() {
        var stack = new ArrayDeque<Double>();
        var pushCommand = new Add(new String[]{});
        assertThrows(NotEnoughArgumentsInStack.class, () -> pushCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }

    @Test
    void addNaturalNums() {
        var stack = new ArrayDeque<Double>();
        var pushCommand = new Push(new String[]{"10"});
        try {
            pushCommand.execute(new MemoryContext(stack, new HashMap<>()));
            assertEquals(10.0, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void pushDefined() {
        var stack = new ArrayDeque<Double>();
        var pushCommand = new Push(new String[]{"a"});
        try {
            pushCommand.execute(new MemoryContext(stack, new HashMap<>() {{
                put("a", 4.0);
            }}));
            assertEquals(4, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void tryToPushNotDefined() {
        var stack = new ArrayDeque<Double>();
        var pushCommand = new Push(new String[]{"a"});
        assertThrows(InvalidCommandArgument.class, () -> pushCommand.execute(new MemoryContext(stack, new HashMap<>())));
    }
}
