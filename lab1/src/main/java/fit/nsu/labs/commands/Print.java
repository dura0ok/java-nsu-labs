package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

import java.io.IOException;

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

        validateNumberOfArgs(0);
        validateMiniumNeededStackSize(context, 1);

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
