package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

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

    public abstract void execute(Context context) throws InvalidCommandArgument;


}
