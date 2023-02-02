package fit.nsu.labs.commands;

import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

public class Push extends Command{

    @Override
    public String getCommandName() {
        return "push";
    }

    public Push(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public void execute(Context context) throws InvalidCommandArgument {
        System.out.println(getCommandName());

        if(getArgs().length != 1){
            throw new InvalidCommandArgument(this.getClass().getName(), "size");
        }

        var arg = getArgs()[0];
        try{
            context.getStack().add(Integer.parseInt(arg));
        }catch (NumberFormatException e){
            if(!context.getDefines().containsKey(arg)){
                throw new InvalidCommandArgument(this.getClass().getName());
            }

            var value = context.getDefines().get(arg);
            context.getStack().add(value);
        }

    }


}
