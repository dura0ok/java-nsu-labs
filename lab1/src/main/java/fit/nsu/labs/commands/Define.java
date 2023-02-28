package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.util.logging.Level;

import static fit.nsu.labs.Utils.isNotDouble;

public class Define extends AbstractCommand {
    public Define(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "Define num to letter. Example: define a 4; push a === push 4";
    }

    @Override
    public void execute(Context context) throws CalcException {
        validateNumberOfArgs(2);
        var key = getArgs()[0];
        var value = getArgs()[1];

        if (context.isDefined(key)) {
            throw new InvalidCommandArgument(getCommandName(), "this variable already defined " + key);
        }

        if (isNotDouble(value)) {
            getLogger().log(Level.WARNING, "this is not a number! You can define only numbers");
            return;
        }

        double numberDefine = Double.parseDouble(value);
        context.defineNumber(key, numberDefine);

    }


}

