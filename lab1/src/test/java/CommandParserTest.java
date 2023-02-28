import fit.nsu.labs.CommandParser;
import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.commands.Subtract;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.ConfigurationException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {


    CommandParser generateParser(String inputData) throws CalcException {
        return new CommandParser(
                new ByteArrayInputStream(inputData.getBytes())
        );
    }

    @Test
    void emptyCommands() {
        try {
            var parser = generateParser("");
            var command = parser.parseCommand();
            assertNull(command);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void singleCorrectCommand() {
        try {
            var parser = generateParser("sqrt arg1 arg2");
            var command = parser.parseCommand();
            assertArrayEquals(command.getArgs(), new String[]{"arg1", "arg2"});

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void singleIncorrectCommandName() throws CalcException {
        var parser = generateParser("sqrt1 arg1 arg2");
        assertThrows(ConfigurationException.class, parser::parseCommand);
    }

    @Test
    void CorrectCommandWithComment() {
        try {
            var parser = generateParser("sqrt arg1\n # comment line \n print");
            var sqrtCommand = parser.parseCommand();
            assertArrayEquals(sqrtCommand.getArgs(), new String[]{"arg1"});
            var printCommand = parser.parseCommand();
            assertEquals(0, printCommand.getArgs().length);
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void CorrectCommandWithMultipleComment() {
        try {
            var parser = generateParser("sqrt arg1\n # comment line \n \n #multiple_comments \n print");
            var sqrtCommand = parser.parseCommand();
            assertArrayEquals(sqrtCommand.getArgs(), new String[]{"arg1"});
            var printCommand = parser.parseCommand();
            assertEquals(0, printCommand.getArgs().length);
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void IncorrectCommandName() {
        try {
            generateParser("data@ arg1 arg2");
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