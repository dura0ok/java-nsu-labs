package fit.nsu.labs;

import java.io.InputStream;

public class CalcEvaluator {
    private final CommandParser parser;

    public CalcEvaluator(InputStream input) {
        parser = new CommandParser(input);
    }

    public void calculate() throws Exception {
        var context = new Context();

        var commands = parser.parseCommands();
        for (var command : commands) {
            command.execute(context);
        }
    }


}
