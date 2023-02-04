package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.EmptyStack;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Sqrt extends Command {

    public Sqrt(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "sqrt";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument, EmptyStack {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        if (context.getStack().isEmpty()) {
            throw new EmptyStack(getCommandName(), "what to print");
        }

        double num = context.getStack().pop();

        if (num < 0) {
            throw new InvalidCommandArgument(getCommandName(), "num must be >= 0");
        }

        context.getStack().push(Math.sqrt(num));
    }


}
