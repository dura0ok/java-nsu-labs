package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.EmptyStack;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Print extends Command {

    public Print(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "print";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument, EmptyStack {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        if (context.getStack().isEmpty()) {
            throw new EmptyStack(getCommandName(), "what to print");
        }
        System.out.println(context.getStack().peek());

    }


}
