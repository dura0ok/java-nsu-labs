package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Sqrt extends AbstractCommand {

    public Sqrt(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "sqrt two numbers";
    }


    @Override
    public void execute(Context context) throws CalcException {

        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 1);

        double num = context.popStack();

        if (num < 0) {
            throw new InvalidCommandArgument(getCommandName(), "num must be >= 0");
        }

        context.pushStack(Math.sqrt(num));
    }


}
