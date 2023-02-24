package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

public class Subtract extends AbstractCommand {
    public Subtract(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "Subtract two numbers from stack. Result put in stack.";
    }

    @Override
    public void execute(Context context) throws CalcException {

        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 2);

        var secondNum = context.popStack();
        var firstNum = context.popStack();
        context.pushStack(firstNum - secondNum);
    }
}
