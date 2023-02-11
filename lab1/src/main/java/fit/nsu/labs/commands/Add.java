package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;
import java.util.logging.Level;

public class Add extends Command {

    public Add(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public void execute(Context context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMiniumNeededStackSize(context, 2);

        var firstNum = context.popStack();
        var secondNum = context.popStack();
        context.pushStack(firstNum + secondNum);
        CalcLogger.getLogger(this.getClass()).log(Level.INFO, "success sum two numbers");
    }


}
