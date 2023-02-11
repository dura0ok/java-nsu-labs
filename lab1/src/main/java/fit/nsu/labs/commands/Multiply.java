package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

public class Multiply extends Command {

    public Multiply(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandName() {
        return "multiply";
    }

    @Override
    public void execute(Context context) throws CalcException {

        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 2);

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum * secondNum);

    }


}
