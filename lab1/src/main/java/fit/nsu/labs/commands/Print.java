package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

import java.io.IOException;
import java.util.logging.Level;

public class Print extends Command {

    public Print(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "print";
    }

    @Override
    public void execute(Context context) throws CalcException, IOException {

        if (getArgs().length != 0) {
            CalcLogger.getLogger(this.getClass()).log(Level.WARNING, "Bad number of arguments Exception");
            throw new BadNumberOfArguments(getCommandName(), 0, getArgs().length);
        }

        if (context.isStackEmpty()) {
            CalcLogger.getLogger(this.getClass()).log(Level.WARNING, "NotEnoughArgumentsInStack");
            throw new NotEnoughArgumentsInStack(getCommandName(), "what to print");
        }
        try {
            System.out.println(context.peekStack());
        } catch (NullPointerException e) {
            throw new NotEnoughArgumentsInStack(
                    getCommandName(),
                    "what to print which need be in stack, but stack empty :C"
            );
        }


    }


}
