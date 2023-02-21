package fit.nsu.labs.commands;

import fit.nsu.labs.exceptions.CalcException;

import java.io.IOException;

public interface Command {
    String getCommandName();

    String getCommandDescription();


    String[] getArgs();

    void execute(Context context) throws CalcException, IOException;
}
