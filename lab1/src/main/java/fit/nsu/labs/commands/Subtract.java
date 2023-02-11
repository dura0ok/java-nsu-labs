package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;

public class Subtract extends Command {

    public Subtract(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "subtract";
    }

    @Override
    public void execute(MemoryContext context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMiniumNeededStackSize(context, 2);

        var secondNum = context.popStack();
        var firstNum = context.popStack();
        context.pushStack(firstNum - secondNum);

    }


}
