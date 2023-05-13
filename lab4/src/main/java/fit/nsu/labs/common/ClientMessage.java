package fit.nsu.labs.common;

import java.io.Serializable;

public abstract sealed class ClientMessage implements Serializable permits ClientMessage.ListMembers, ClientMessage.LoginRequest, ClientMessage.Logout, ClientMessage.Message {
    private final Type type;
    private final String name;

    ClientMessage(Type type, String name) {
        this.type = type;
        ;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    ;

    public String getName() {
        return name;
    }

    public enum Type {
        Command,
        Event
    }

    public static final class LoginRequest extends ClientMessage {
        private final String userName;

        public LoginRequest(String userName) {
            super(Type.Command, "login");
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }
    }

    public static final class Message extends ClientMessage {
        private final String messageText;
        private final int sessionID;

        public Message(String messageText, int sessionID) {
            super(Type.Command, "message");
            this.messageText = messageText;
            this.sessionID = sessionID;
        }

        public String getMessageText() {
            return messageText;
        }

        public int getSessionID() {
            return sessionID;
        }
    }

    public static final class Logout extends ClientMessage {
        private final int sessionID;

        public Logout(int sessionID) {
            super(Type.Command, "logout");
            this.sessionID = sessionID;
        }

        public int getSessionID() {
            return sessionID;
        }
    }

    public static final class ListMembers extends ClientMessage {
        private final int sessionID;

        public ListMembers(int sessionID) {
            super(Type.Command, "list");
            this.sessionID = sessionID;
        }

        public int getSessionID() {
            return sessionID;
        }
    }
}