package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.io.IOException;

public class Divide extends Command {

    public Divide(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "divide";
    }

    @Override
    public void execute(MemoryContext context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMiniumNeededStackSize(context, 2);
        
        var secondNum = context.popStack();
        var firstNum = context.popStack();
        if (secondNum == 0) {
            throw new InvalidCommandArgument(getCommandName(), "second arg in stack(division by zero)");
        }
        context.pushStack(firstNum / secondNum);

    }


}
