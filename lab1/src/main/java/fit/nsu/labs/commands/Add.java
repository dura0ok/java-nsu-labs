package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;

public class Add extends AbstractCommand {

    public Add(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "add two numbers";
    }

    @Override
    public void execute(Context context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 2);

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum + secondNum);

    }


}
