package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Define extends Command{

    @Override
    public String getCommandName() {
        return "define";
    }

    public Define(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument {

        if(getArgs().length != 2){
            throw new InvalidCommandArgument(this.getClass().getName(), "size");
        }

        var key = getArgs()[0];
        var value = getArgs()[1];

        context.getDefines().put(key, Integer.valueOf(value));


    }


}
