package fit.nsu.labs;

import fit.nsu.labs.exceptions.EmptyStack;
import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class CalcEvaluator {
    private final CommandParser parser;

    public CalcEvaluator(InputStream input) {
        parser = new CommandParser(input);
    }

    public void calculate() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidCommandArgument, EmptyStack {
        var context = new Context();

        var commands = parser.parseCommands();
        for (var command : commands) {
            command.execute(context);
        }
    }


}
