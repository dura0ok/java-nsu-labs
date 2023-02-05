package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

import java.io.IOException;
import java.util.logging.Level;

public class Multiply extends Command {

    public Multiply(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "multiply";
    }

    @Override
    public void execute(Context context) throws CalcException, IOException {

        if (getArgs().length != 0) {
            CalcLogger.getLogger(this.getClass()).log(Level.WARNING, "Bad number of arguments Exception");
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }

        if (context.getStackSize() < 2) {
            CalcLogger.getLogger(this.getClass()).log(Level.WARNING, "NotEnoughArgumentsInStack");
            throw new NotEnoughArgumentsInStack(getCommandName(), "what to multiply");
        }

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum * secondNum);

    }


}
