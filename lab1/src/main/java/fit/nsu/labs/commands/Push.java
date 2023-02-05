package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Push extends Command {

    public Push(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "push";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument {

        if (getArgs().length != 1) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        var arg = getArgs()[0];
        try {
            context.pushStack(Double.parseDouble(arg));
        } catch (NumberFormatException e) {
            if (!context.isDefined(arg)) {
                throw new InvalidCommandArgument(
                        getCommandName(), "push argument must be number or defined value"
                );
            }

            var value = context.getDefinedByKey(arg);
            context.pushStack(value);
        }

    }


}
