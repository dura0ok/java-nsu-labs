package fit.nsu.labs.common;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;
import java.util.List;

public abstract sealed class ServerMessage implements Serializable
        permits ServerMessage.EmptySuccess, ServerMessage.Error, ServerMessage.ListMembers,
        ServerMessage.ListMessages, ServerMessage.LoginResponse, ServerMessage.NewMessage {
    @XStreamOmitField
    private final ErrorType errorType;

    protected ServerMessage(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public enum ErrorType {
        ERROR, SUCCESS
    }

    public static final class LoginResponse extends ServerMessage {
        private final int sessionID;

        public LoginResponse(int sessionId) {
            super(ErrorType.SUCCESS);
            sessionID = sessionId;
        }

        public int getSessionID() {
            return sessionID;
        }
    }

    public static final class Error extends ServerMessage {
        private final String message;

        public Error(String message) {
            super(ErrorType.ERROR);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public static final class EmptySuccess extends ServerMessage {

        EmptySuccess() {
            super(ErrorType.SUCCESS);
        }
    }

    public static final class ListMembers extends ServerMessage {
        private final List<User> userList;

        public ListMembers(List<User> userList) {
            super(ErrorType.SUCCESS);
            this.userList = userList;
        }

        public List<User> getUserList() {
            return userList;
        }
    }

    public static final class ListMessages extends ServerMessage {
        private final List<TextMessage> messages;

        public ListMessages(List<TextMessage> messages) {
            super(ErrorType.SUCCESS);
            this.messages = messages;
        }

        public List<TextMessage> getMessages() {
            return messages;
        }
    }

    public static final class NewMessage extends ServerMessage {
        private final TextMessage message;

        public NewMessage(TextMessage message) {
            super(ErrorType.SUCCESS);
            this.message = message;
        }

        public TextMessage getMessage() {
            return message;
        }
    }

}

