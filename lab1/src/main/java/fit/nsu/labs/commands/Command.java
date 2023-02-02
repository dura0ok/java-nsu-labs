package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public abstract class Command {
    final private String[] args;

    Command(String[] inputArgs) {
        args = inputArgs;
    }


    abstract public String getCommandName();


    public String[] getArgs() {
        return args;
    }

    abstract public void execute(Context context) throws InvalidCommandArgument;
}
