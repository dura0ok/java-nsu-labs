package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

import java.io.IOException;
import java.util.logging.Logger;


// todo: problem, no interface for Command

/**
 * Abstract class Command which describes interface to commands and set arg fields
 */
public abstract class AbstractCommand implements Command {
    // todo: problem
    public final Logger logger;
    private final String[] args;


    AbstractCommand(String[] inputArgs) throws CalcException {
        args = inputArgs;
        logger = CalcLogger.getLogger(this.getClass());
    }


    public String getCommandName() {
        return this.getClass().getName();
    }

    public abstract String getCommandDescription();


    public String[] getArgs() {
        return args;
    }

    public abstract void execute(Context context) throws CalcException, IOException;

    // todo: problem
    public void validateNumberOfArgs(int numberNeededArgs) throws BadNumberOfArguments {
        if (getArgs().length != numberNeededArgs) {
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }
    }

    // todo: problem
    public void validateMinimumNeededStackSize(Context context, int numberNeededElements) throws NotEnoughArgumentsInStack {
        if (context.getStackSize() < numberNeededElements) {
            throw new NotEnoughArgumentsInStack(getCommandName(), numberNeededElements, context.getStackSize());
        }

    }


}
