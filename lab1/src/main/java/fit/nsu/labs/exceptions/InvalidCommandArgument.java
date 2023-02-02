package fit.nsu.labs.exceptions;

public class InvalidCommandArgument extends Exception{
    public InvalidCommandArgument(String commandName){
        super("Invalid argument in command: " + commandName);
    }

    public InvalidCommandArgument(String commandName, String argName){
        super(String.format("Invalid argument(%s) in command %s", argName, commandName));
    }
}
