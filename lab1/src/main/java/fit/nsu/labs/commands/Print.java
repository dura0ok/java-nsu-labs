package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;
import fit.nsu.labs.exceptions.NotEnoughArgumentsInStack;

public class Print extends AbstractCommand {
    public Print(String[] inputArgs) throws CalcException {
        super(inputArgs);
    }

    @Override
    public String getCommandDescription() {
        return "Print num from stack. Not pop!";
    }

    @Override
    public void execute(Context context) throws CalcException {
        validateNumberOfArgs(0);
        validateMinimumNeededStackSize(context, 1);

        try {
            System.out.println(context.peekStack());
        } catch (CalcException e) {
            throw new NotEnoughArgumentsInStack(
                    getCommandName(),
                    "Nothing to print. Stack empty."
            );
        }
    }
}
