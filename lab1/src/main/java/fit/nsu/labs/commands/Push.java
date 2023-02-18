package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.io.IOException;
import java.util.logging.Level;

public class Push extends Command {

    public Push(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }


    @Override
    public void execute(Context context) throws CalcException, IOException {

        validateNumberOfArgs(1);

        var arg = getArgs()[0];
        try {
            context.pushStack(Double.parseDouble(arg));
        } catch (NumberFormatException e) {
            if (!context.isDefined(arg)) {
                logger.log(Level.WARNING,
                        "Exception: InvalidCommandArgument. Push argument must be number or defined value");
                throw new InvalidCommandArgument(
                        getCommandName(), "push argument must be number or defined value"
                );
            }

            try {
                var value = context.getDefinedByKey(arg);
                context.pushStack(value);
            } catch (NullPointerException exception) {
                throw new InvalidCommandArgument(getCommandName(), "what to define");
            }


        }

    }


}
