package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Add extends Command {

    public Add(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "add";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument, NotEnoughtArgumentsInStack {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        if (context.getStack().size() < 2) {
            throw new NotEnoughtArgumentsInStack(getCommandName(), "what to add");
        }

        var firstNum = context.getStack().pop();
        var secondNum = context.getStack().pop();
        context.getStack().push(firstNum + secondNum);

    }


}
