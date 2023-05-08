package fit.nsu.labs.common;

import java.io.Serializable;

public record ClientMessage(MessageType type, Integer sessionID, String body) implements Serializable {
    public enum MessageType {
        LOGIN, LIST, MESSAGE, LOGOUT, USER_LOGIN
    }
}