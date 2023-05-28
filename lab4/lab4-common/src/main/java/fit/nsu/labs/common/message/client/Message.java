package fit.nsu.labs.common.message.client;

import java.util.Objects;

public class Message extends ClientMessage {
    private String messageText;
    private int sessionID;

    public Message() {
        super(Type.COMMAND, "message");
    }

    public Message(String messageText, int sessionID) {
        this();
        setMessageText(messageText);
        setSessionID(sessionID);
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getSessionID() == message.getSessionID() && Objects.equals(getMessageText(), message.getMessageText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessageText(), getSessionID());
    }
}
