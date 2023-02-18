package fit.nsu.labs.exceptions;

public class InvalidCommandArgument extends CalcException {
    public InvalidCommandArgument(String commandName) {
        super("Invalid argument in command: " + commandName);
    }

    public InvalidCommandArgument(String commandName, String argName) {
        super(String.format("Invalid argument in command %s. Problem with %s", commandName, argName));
    }
}
