package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

public class Multiply extends Command {

    public Multiply(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "multiply";
    }

    @Override
    public void execute(Context context) throws CalcException {

        if (getArgs().length != 0) {
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }

        if (context.getStackSize() < 2) {
            throw new NotEnoughArgumentsInStack(getCommandName(), "what to multiply");
        }

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum * secondNum);

    }


}
