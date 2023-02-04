import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Print;
import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PrintCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }


    @Test
    void printNonEmptyElementInStack() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        var printCommand = new Print(new String[]{});
        try {
            printCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(String.valueOf(8.0), outputStreamCaptor.toString().trim());
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void printEmptyElementInStack() {
        var stack = new ArrayDeque<Double>();
        var printCommand = new Print(new String[]{});
        assertThrows(NotEnoughtArgumentsInStack.class, () -> printCommand.execute(new Context(stack, new HashMap<>())));
    }
}
