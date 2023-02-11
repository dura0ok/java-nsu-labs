package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;
import java.util.logging.Level;

public class Add extends Command {

    public Add(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public void execute(Context context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 2);

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum + secondNum);
        logger.log(Level.INFO, "success sum two numbers");
    }


}
