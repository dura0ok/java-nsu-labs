package fit.nsu.labs.commands;

import fit.nsu.labs.CalcLogger;
import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.io.IOException;
import java.util.logging.Level;

public class Push extends Command {

    public Push(String[] inputArgs) {
        super(inputArgs);
    }

    @Override
    public String getCommandName() {
        return "push";
    }

    @Override
    public void execute(MemoryContext context) throws CalcException, IOException {

        validateNumberOfArgs(1);

        var arg = getArgs()[0];
        try {
            context.pushStack(Double.parseDouble(arg));
        } catch (NumberFormatException e) {
            if (!context.isDefined(arg)) {
                CalcLogger.getLogger(this.getClass()).log(Level.WARNING,
                        "Exception: InvalidCommandArgument. Push argument must be number or defined value");
                throw new InvalidCommandArgument(
                        getCommandName(), "push argument must be number or defined value"
                );
            }

            var value = context.getDefinedByKey(arg);
            context.pushStack(value);
        }

    }


}
