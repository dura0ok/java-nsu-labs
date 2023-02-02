package fit.nsu.labs;

import fit.nsu.labs.exceptions.InvalidCommandArgument;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class CalcEvaluator {
    private final CommandParser parser;

    CalcEvaluator(InputStream input) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvalidCommandArgument {
        parser = new CommandParser(input);
        var context = new Context();

        var commands = parser.parseCommands();
        for (var command : commands) {
            command.execute(context);
        }
        System.out.println("asd");
    }


}
