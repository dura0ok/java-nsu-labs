package fit.nsu.labs.exceptions;

public class NotEnoughtArgumentsInStack extends Exception {
    public NotEnoughtArgumentsInStack(String commandName) {
        super("Empty Stack when trying to get arguments in command: " + commandName);
    }

    public NotEnoughtArgumentsInStack(String commandName, String argName) {
        super(String.format("Empty Stack when trying to get argument %s in command %s", argName, commandName));
    }
}
