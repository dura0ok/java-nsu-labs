package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

public class Subtract extends AbstractCommand {
    public Subtract(String[] inputArgs) throws CalcException {
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
        return "Subtract two numbers from stack. Result put in stack.";
    }

    @Override
    public void execute(Context context) throws CalcException {
        var secondNum = context.popStack();
        var firstNum = context.popStack();
        context.pushStack(firstNum - secondNum);
    }
}
