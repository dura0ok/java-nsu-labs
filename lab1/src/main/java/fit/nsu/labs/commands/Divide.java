package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;
import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;

public class Divide extends Command {

    public Divide(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "divide";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument, NotEnoughtArgumentsInStack {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        if (context.getStack().size() < 2) {
            throw new NotEnoughtArgumentsInStack(getCommandName(), "what to divide");
        }

        var secondNum = context.getStack().pop();
        var firstNum = context.getStack().pop();
        if (secondNum == 0) {
            throw new InvalidCommandArgument(getCommandName(), "second arg in stack(division by zero)");
        }
        context.getStack().push(firstNum / secondNum);

    }


}
