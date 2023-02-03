package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Sqrt extends Command {

    public Sqrt(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "sqrt";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(this.getClass().getName(), "size");
        }

        var num = context.getStack().pop();
        context.getStack().push(Math.sqrt(num));
    }


}
