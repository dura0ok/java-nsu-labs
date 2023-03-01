package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

public class Multiply extends AbstractCommand {
    public Multiply(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public int getNumberNeededArgs() {
        return 0;
    }

    @Override
    public int getNumberMinimumNeededStackSize() {
        return 2;
    }

    @Override
    public String getCommandDescription() {
        return "multiply two numbers from stack. Result put in stack.";
    }

    @Override
    public void execute(Context context) throws CalcException {
        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum * secondNum);

    }
}
