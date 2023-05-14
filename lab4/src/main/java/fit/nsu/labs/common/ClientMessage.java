package fit.nsu.labs.common;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import static fit.nsu.labs.Utils.getBytes;

public abstract sealed class ClientMessage implements Serializable permits ClientMessage.ListMembers, ClientMessage.LoginRequest, ClientMessage.Logout, ClientMessage.Message {
    private static final XStream xStream = new XStream();
    private final Type type;
    private final String name;


    ClientMessage(Type type, String name) {
        this.type = type;
        ;
        this.name = name;
    }

    public static byte[] serialize(ClientMessage message) throws IOException {
        xStream.addPermission(AnyTypePermission.ANY);
        switch (System.getProperty("PROTOCOL")) {
            case "XML" -> {
                return xStream.toXML(message).getBytes();
            }
            case "SERIALIZATION" -> {
                return getBytes(message);
            }

            default -> throw new RuntimeException("invalid protocol");
        }
    }

    ;

    public static ClientMessage deserialize(InputStream input) throws IOException, ClassNotFoundException {
        xStream.addPermission(AnyTypePermission.ANY);
        switch (System.getProperty("PROTOCOL")) {
            case "SERIALIZATION" -> {
                return (ClientMessage) new ObjectInputStream(input).readObject();
            }
            case "XML" -> {
                return (ClientMessage) xStream.fromXML(input);
            }

            default -> throw new RuntimeException("invalid protocol");
        }
    }

    public Type getType() {
        return type;
    }

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

        public Message(int sessionID, String messageText) {
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