package fit.nsu.labs.exceptions;

public class InvalidArgument extends MineSweeperGameException {
    public InvalidArgument(String message) {
        super(String.format("Invalid argument. %s", message));
    }
}
