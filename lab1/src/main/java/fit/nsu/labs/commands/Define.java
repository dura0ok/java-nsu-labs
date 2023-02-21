package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.util.logging.Level;

public class Define extends AbstractCommand {

    public Define(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandName() {
        return "define";
    }

    @Override
    public String getCommandDescription() {
        return "define";
    }

    @Override
    public void execute(Context context) throws CalcException {

        validateNumberOfArgs(2);
        var key = getArgs()[0];
        var value = getArgs()[1];

        try {
            double numberDefine = Double.parseDouble(value);
            if (context.isDefined(key)) {
                throw new InvalidCommandArgument("this variable already defined " + key);
            }
            context.defineNumber(key, numberDefine);
        } catch (NumberFormatException exception) {
            logger.log(Level.WARNING, exception.getMessage());
        }
    }


}