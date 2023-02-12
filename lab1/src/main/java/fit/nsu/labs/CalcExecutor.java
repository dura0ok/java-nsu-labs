package fit.nsu.labs;

import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.exceptions.CalcException;

import java.io.InputStream;
import java.util.logging.Level;

public class CalcExecutor {
    private final CommandParser parser;

    public CalcExecutor(InputStream input) {
        parser = new CommandParser(input);
    }

    public void calculate() throws Exception {
        var context = new MemoryContext();
        CalcLogger.getLogger(this.getClass()).log(Level.INFO, "start parsing commands");
        var commands = parser.parseCommands();
        CalcLogger.getLogger(this.getClass()).log(Level.INFO, "end parsing commands");
        for (var command : commands) {
            CalcLogger.getLogger(this.getClass()).log(Level.INFO, "Start execute: " + command.getCommandName());
            try {
                command.execute(context);
            } catch (CalcException e) {
                System.err.println(e.getMessage());
            }

        }
        CalcLogger.getLogger(this.getClass()).log(Level.INFO, "Execute all commands. Finish");
    }


}
