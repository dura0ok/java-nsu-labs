package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Divide extends Command {

    public Divide(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "divide";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(this.getClass().getName(), "size");
        }

        var secondNum = context.getStack().pop();
        var firstNum = context.getStack().pop();
        if(secondNum == 0){
            throw new InvalidCommandArgument(this.getClass().getName(), "second arg in stack(division by zero)");
        }
        context.getStack().push(firstNum / secondNum);

    }


}
