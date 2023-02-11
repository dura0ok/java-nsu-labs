package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

import java.io.IOException;

/**
 * Abstract class Command which describes interface to commands and set arg fields
 */
public abstract class Command {
    private final String[] args;

    Command(String[] inputArgs) {
        args = inputArgs;
    }


    public abstract String getCommandName();


    public String[] getArgs() {
        return args;
    }

    public abstract void execute(Context context) throws CalcException, IOException;

    public void validateNumberOfArgs(int numberNeededArgs) throws BadNumberOfArguments {
        if (getArgs().length != numberNeededArgs) {
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }
    }

    public void validateMiniumNeededStackSize(Context context, int numberNeededElements) throws NotEnoughArgumentsInStack {
        if (context.getStackSize() < numberNeededElements) {
            throw new NotEnoughArgumentsInStack(getCommandName(), numberNeededElements, context.getStackSize());
        }

    }


}
