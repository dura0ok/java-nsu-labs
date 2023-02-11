package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;

public class Define extends Command {

    public Define(String[] inputArgs) {
        super(inputArgs);

    }

    @Override
    public String getCommandName() {
        return "define";
    }

    @Override
    public void execute(Context context) throws CalcException, IOException {

        validateNumberOfArgs(2);
        var key = getArgs()[0];
        var value = getArgs()[1];

        context.defineNumber(key, Double.parseDouble(value));
    }


}
