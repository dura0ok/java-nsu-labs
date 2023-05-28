package fit.nsu.labs.common.message.server;

import fit.nsu.labs.common.TextMessage;

import java.util.Objects;

public class NewMessage extends ServerMessage {
    private TextMessage message;

    public NewMessage() {
        super(ErrorType.EVENT);
    }

    public NewMessage(TextMessage message) {
        this();
        setMessage(message);
    }

    public TextMessage getMessage() {
        return message;
    }

    public void setMessage(TextMessage message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewMessage that = (NewMessage) o;
        return Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }
}
