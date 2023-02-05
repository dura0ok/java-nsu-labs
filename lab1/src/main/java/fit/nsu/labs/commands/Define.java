package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Define extends Command {

    public Define(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "define";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument {

        if (getArgs().length != 2) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        var key = getArgs()[0];
        var value = getArgs()[1];

        context.defineNumber(key, Double.parseDouble(value));
    }


}
