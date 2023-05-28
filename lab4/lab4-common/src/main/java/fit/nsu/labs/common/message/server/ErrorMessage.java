package fit.nsu.labs.common.message.server;

import java.util.Objects;

public class ErrorMessage extends ServerMessage {
    private String message;

    public ErrorMessage() {
        super(ErrorType.ERROR);
    }

    public ErrorMessage(String message) {
        this();
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }
}
