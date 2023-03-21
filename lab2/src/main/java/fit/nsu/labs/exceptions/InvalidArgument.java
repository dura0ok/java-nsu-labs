package fit.nsu.labs.exceptions;


public class InvalidArgument extends MineSweeperGameException {
    public InvalidArgument(String message) {
        super(String.format("Invalid argument. %s", message));
    }

    public InvalidArgument(String message, Throwable cause) {
        super(String.format("Invalid argument. %s", message), cause);
    }
}