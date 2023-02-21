package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Divide extends AbstractCommand {

    public Divide(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "divide two numbers";
    }


    @Override
    public void execute(Context context) throws CalcException {

        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 2);

        var secondNum = context.popStack();
        var firstNum = context.popStack();
        if (Double.compare(secondNum, 0) == 0) {
            throw new InvalidCommandArgument(getCommandName(), "second arg in stack(division by zero)");
        }
        context.pushStack(firstNum / secondNum);

    }


}
