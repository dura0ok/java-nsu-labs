import fit.nsu.labs.Context;
import fit.nsu.labs.commands.Define;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.HashMap;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class DefineCommandTest {
    @Test
    void defineOneCorrectConst() {
        var defines = new HashMap<String, Double>();
        var defineCommand = new Define(new String[]{"a", "4.0"});
        try {
            defineCommand.execute(new Context(new ArrayDeque<>(), defines));
            assertTrue(defines.containsKey("a"));
            assertEquals(4.0, defines.get("a"));
        } catch (Exception ignored) {
            fail();
        }
    }

    @Test
    void TryToDefineWithoutArgs() {
        var defines = new HashMap<String, Double>();
        var defineCommand = new Define(new String[]{});
        assertThrows(BadNumberOfArguments.class, () -> defineCommand.execute(new Context(new ArrayDeque<>(), defines)));
    }

    @Test
    void TryToDefineWithoutValue() {
        var defines = new HashMap<String, Double>();
        var defineCommand = new Define(new String[]{"a"});
        assertThrows(BadNumberOfArguments.class, () -> defineCommand.execute(new Context(new ArrayDeque<>(), defines)));
    }


}
