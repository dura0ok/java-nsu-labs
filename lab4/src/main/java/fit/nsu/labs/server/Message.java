package fit.nsu.labs.server;

public record Message(MessageType type, String body, String name) {
    public enum MessageType{
        LOGIN, MESSAGE, INFO, LOGOUT;
    }
}