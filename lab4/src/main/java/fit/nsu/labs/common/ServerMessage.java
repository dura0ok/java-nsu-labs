package fit.nsu.labs.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

import static fit.nsu.labs.Utils.getBytes;

public abstract sealed class ServerMessage implements Serializable
        permits ServerMessage.EmptySuccess, ServerMessage.Error, ServerMessage.ListMembers,
        ServerMessage.ListMessages, ServerMessage.LoginResponse, ServerMessage.NewMessage {
    private static final XStream xStream = new XStream();
    @XStreamOmitField
    private final ErrorType errorType;

    protected ServerMessage(ErrorType errorType) {
        this.errorType = errorType;
    }

    public static byte[] serialize(ServerMessage message) throws IOException {
        xStream.addPermission(AnyTypePermission.ANY);
        switch (System.getProperty("PROTOCOL")) {
            case "XML" -> {
                var msg = xStream.toXML(message);
                System.out.println(msg);
                return msg.getBytes();
            }
            case "SERIALIZATION" -> {
                return getBytes(message);
            }

            default -> throw new RuntimeException("invalid protocol");
        }
    }

    public static ServerMessage deserialize(InputStream input) throws IOException, ClassNotFoundException {
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.registerConverter(new UserConverter());
        xStream.registerConverter(new TextMessageConverter());
        switch (System.getProperty("PROTOCOL")) {
            case "SERIALIZATION" -> {
                return (ServerMessage) new ObjectInputStream(input).readObject();
            }
            case "XML" -> {
                return (ServerMessage) xStream.fromXML(input);
            }

            default -> throw new RuntimeException("invalid protocol");
        }
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

