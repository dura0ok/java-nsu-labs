import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Print;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PrintCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    void printNonEmptyElementInStack() {
        var stack = new ArrayDeque<Double>();
        stack.push(8.0);
        var printCommand = new Print(new String[]{});
        try {
            printCommand.execute(new Context(stack, new HashMap<>()));
            assertEquals(String.valueOf(8.0), outContent.toString());
        } catch (Exception ignored) {
            fail();
        }

    }
}
