package fit.nsu.labs.exceptions;

public class LogConfigNotFound extends CalcException {
    public LogConfigNotFound() {
        super("Error with trying open log file. File not found or we not have permissions to open it.");
    }
}
