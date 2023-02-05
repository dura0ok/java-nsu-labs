package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;
import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;

public class Subtract extends Command {

    public Subtract(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "subtract";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument, NotEnoughtArgumentsInStack {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        if (context.getStackSize() < 2) {
            throw new NotEnoughtArgumentsInStack(getCommandName(), "what to subtract");
        }


        var secondNum = context.popStack();
        var firstNum = context.popStack();
        context.pushStack(firstNum - secondNum);

    }


}
