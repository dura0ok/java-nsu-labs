package fit.nsu.labs.exceptions;


public class InvalidArgument extends MineSweeperGameException {
    // TODO: consider using IllegalArgumentException instead of this class.
    // TODO: at least in some places (maybe not all of them) it's a direct replacement.
    public InvalidArgument(String message) {
        super(String.format("Invalid argument. %s", message));
    }

    public InvalidArgument(String message, Throwable cause) {
        super(String.format("Invalid argument. %s", message), cause);
    }
}