package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

public class Multiply extends AbstractCommand {
    public Multiply(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "multiply two numbers from stack. Result put in stack.";
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
