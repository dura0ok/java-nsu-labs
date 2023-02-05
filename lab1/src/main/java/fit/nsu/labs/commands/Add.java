package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

public class Add extends Command {

    public Add(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public void execute(Context context) throws CalcException {

        if (getArgs().length != 0) {
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }

        if (context.getStackSize() < 2) {
            throw new NotEnoughArgumentsInStack(getCommandName(), "what to add");
        }

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum + secondNum);

    }


}
