package fit.nsu.labs.common.message.server;

import java.io.Serializable;

public abstract class ServerMessage implements Serializable {
    private final ErrorType errorType;

    public ServerMessage(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public enum ErrorType {
        ERROR,
        SUCCESS,
        EVENT
    }
}
