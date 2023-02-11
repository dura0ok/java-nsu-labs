package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;

public class Multiply extends Command {

    public Multiply(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "multiply";
    }

    @Override
    public void execute(MemoryContext context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMiniumNeededStackSize(context, 2);

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum * secondNum);

    }


}
