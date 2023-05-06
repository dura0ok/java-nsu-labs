package fit.nsu.labs.server;

import java.io.Serializable;

public record Message(MessageType type, String body, String name) implements Serializable {
    public enum MessageType {
        LOGIN, MESSAGE, INFO, LOGOUT;
    }
}