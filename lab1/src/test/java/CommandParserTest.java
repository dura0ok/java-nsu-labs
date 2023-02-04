import fit.nsu.labs.CommandParser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {


    CommandParser generateParser(String inputData) {
        return new CommandParser(
                new ByteArrayInputStream(inputData.getBytes())
        );
    }

    @Test
    void emptyCommands() {
        try {
            var parser = generateParser("");
            var commands = parser.parseCommands();
            assertTrue(commands.isEmpty());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void singleCorrectCommand() {
        try {
            var parser = generateParser("sqrt arg1 arg2");
            var commands = parser.parseCommands();
            assertEquals(1, commands.size());
            assertArrayEquals(commands.get(0).getArgs(), new String[]{"arg1", "arg2"});

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void singleIncorrectCommandName() {
        try {
            var parser = generateParser("sqrt1 arg1 arg2");
            parser.parseCommands();
            fail();
        } catch (Exception ignored) {

        }

    }

    @Test
    void CorrectCommandWithComment() {
        try {
            var parser = generateParser("sqrt arg1\n # comment line \n print");
            var commands = parser.parseCommands();
            assertEquals(2, commands.size());
            assertArrayEquals(commands.get(0).getArgs(), new String[]{"arg1"});
            assertEquals(0, commands.get(1).getArgs().length);
        } catch (Exception ignored) {
            fail();
        }

    }

    @Test
    void CorrectCommandWithMultipleComment() {
        try {
            var parser = generateParser("sqrt arg1\n # comment line \n \n #multiple_comments \n print");
            var commands = parser.parseCommands();
            assertEquals(2, commands.size());
            assertArrayEquals(commands.get(0).getArgs(), new String[]{"arg1"});
            assertEquals(0, commands.get(1).getArgs().length);
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
}