package fit.nsu.labs;

import fit.nsu.labs.commands.Command;
import fit.nsu.labs.commands.MemoryContext;
import fit.nsu.labs.exceptions.CalcException;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalcExecutor {
    private final Logger logger;
    private final CommandParser parser;

    public CalcExecutor(InputStream input) throws CalcException {
        parser = new CommandParser(input);
        logger = CalcLogger.getLogger(this.getClass());
    }

    public void calculate() throws Exception {
        var context = new MemoryContext();
        logger.log(Level.INFO, "start execute commands");
        Command command;
        while ((command = parser.parseCommand()) != null) {
            logger.log(Level.INFO, "Start execute: " + command.getCommandName() + "\n");
            try {
                command.execute(context);
                logger.log(Level.INFO, "end execute without errors");
            } catch (CalcException e) {
                logger.warning(e.getMessage());
            }

        }
        logger.log(Level.INFO, "Execute all commands. Finish");
    }
}
