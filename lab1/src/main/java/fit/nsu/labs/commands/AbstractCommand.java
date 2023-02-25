package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

import java.util.logging.Logger;


// todo: problem, no interface for Command
// solved as add interface for command
public abstract class AbstractCommand implements Command {
    // todo: problem
    // Solve: add protected
    protected final Logger logger;
    private final String[] args;

    AbstractCommand(String[] inputArgs) throws CalcException {
        args = inputArgs;
        logger = CalcLogger.getLogger(this.getClass());
    }

    protected static boolean isNotDouble(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    public abstract String getCommandDescription();

    public abstract void execute(Context context) throws CalcException;

    public String getCommandName() {
        return this.getClass().getName();
    }

    public String[] getArgs() {
        return args;
    }

    // todo: problem
    // Solve: add protected
    protected void validateNumberOfArgs(int numberNeededArgs) throws BadNumberOfArguments {
        if (getArgs().length != numberNeededArgs) {
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }
    }

    // todo: problem
    // Solve: add protected
    protected void validateMinimumNeededStackSize(Context context, int numberNeededElements) throws NotEnoughArgumentsInStack {
        if (context.getStackSize() < numberNeededElements) {
            throw new NotEnoughArgumentsInStack(getCommandName(), numberNeededElements, context.getStackSize());
        }
    }

}