package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.Context;
import fit.nsu.labs.exceptions.BadNumberOfArguments;
import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;
import java.util.logging.Level;

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

        if (getArgs().length != 2) {
            CalcLogger.getLogger(this.getClass()).log(Level.WARNING, "Bad number of arguments Exception");
            throw new BadNumberOfArguments(getCommandName(), 2, getArgs().length);
        }

        var key = getArgs()[0];
        var value = getArgs()[1];

        context.defineNumber(key, Double.parseDouble(value));
    }


}
