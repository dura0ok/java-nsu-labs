import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Sqrt;
import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;
import fit.nsu.labs.exceptions.InvalidCommandArgument;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SqrtCommandTest {

    @Test
    void sqrtNaturalSquare() {
        var stack = new ArrayDeque<Double>();
        stack.push(16.0);
        var addCommand = new Sqrt(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(4, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void sqrtRealNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(2.25);
        var addCommand = new Sqrt(new String[]{});
        try {
            addCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(1.5, stack.pop());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void sqrtNegativeNumber() {
        var stack = new ArrayDeque<Double>();
        stack.push(-1.0);
        var sqrtCommand = new Sqrt(new String[]{});
        assertThrows(InvalidCommandArgument.class, () -> sqrtCommand.execute(new Context(stack, new HashMap<>())));

    }

    @Test
    void emptyStackError() {
        var stack = new ArrayDeque<Double>();
        var sqrtCommand = new Sqrt(new String[]{});
        assertThrows(NotEnoughtArgumentsInStack.class, () -> sqrtCommand.execute(new Context(stack, new HashMap<>())));
    }
}
