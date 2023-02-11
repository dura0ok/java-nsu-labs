package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.io.IOException;
import java.util.logging.Level;

public class Sqrt extends Command {

    public Sqrt(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "sqrt";
    }

    @Override
    public void execute(MemoryContext context) throws CalcException, IOException {

        validateNumberOfArgs(0);
        validateMiniumNeededStackSize(context, 1);

        double num = context.popStack();

        if (num < 0) {
            CalcLogger.getLogger(this.getClass()).log(Level.WARNING, "InvalidCommandArgument. Num must be >= 0");
            throw new InvalidCommandArgument(getCommandName(), "num must be >= 0");
        }

        context.pushStack(Math.sqrt(num));
    }


}
