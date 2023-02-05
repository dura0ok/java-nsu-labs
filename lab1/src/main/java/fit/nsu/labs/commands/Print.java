package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;
import fit.nsu.labs.exceptions.NotEnoughtArgumentsInStack;

public class Print extends Command {

    public Print(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "print";
    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument, NotEnoughtArgumentsInStack {

        if (getArgs().length != 0) {
            throw new InvalidCommandArgument(getCommandName(), "size");
        }

        if (context.isStackEmpty()) {
            throw new NotEnoughtArgumentsInStack(getCommandName(), "what to print");
        }
        try{
            System.out.println(context.peekStack());
        }catch (NullPointerException e){
            throw new NotEnoughtArgumentsInStack(
                    getCommandName(),
                    "what to print which need be in stack, but stack empty :C"
            );
        }


    }


}
