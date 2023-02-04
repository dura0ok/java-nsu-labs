package fit.nsu.labs.exceptions;

public class EmptyStack extends Exception {
    public EmptyStack(String commandName) {
        super("Empty Stack when trying to get arguments in command: " + commandName);
    }

    public EmptyStack(String commandName, String argName) {
        super(String.format("Empty Stack when trying to get argument %s in command %s", argName, commandName));
    }
}
