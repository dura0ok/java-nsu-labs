package fit.nsu.labs.common;

import fit.nsu.labs.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


public abstract sealed class ClientMessage implements Serializable permits ClientMessage.ListMembers, ClientMessage.LoginRequest, ClientMessage.Logout, ClientMessage.Message {
    private final Type type;
    private final String name;


    ClientMessage(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public static byte[] serialize(ClientMessage message) throws IOException {
        try {
            switch (System.getProperty("PROTOCOL")) {
                case "XML" -> {
                    return serializeXML(message).getBytes();
                }
                case "SERIALIZATION" -> {
                    return Utils.getBytes(message);
                }
                default -> throw new RuntimeException("invalid protocol");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String serializeXML(ClientMessage message) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element rootElement = document.createElement("command");
        rootElement.setAttribute("name", message.getName());

        if (message instanceof ClientMessage.LoginRequest loginRequest) {
            serializeLoginRequest(document, rootElement, loginRequest);
        } else if (message instanceof ClientMessage.ListMembers listMembers) {
            serializeListMembers(document, rootElement, listMembers);
        } else if (message instanceof ClientMessage.Message msg) {
            serializeMessage(document, rootElement, msg);
        } else if (message instanceof ClientMessage.Logout logout) {
            serializeLogout(document, rootElement, logout);
        }

        document.appendChild(rootElement);

        StringWriter writer = new StringWriter();
        try {
            Utils.writeDocument(document, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return writer.toString();
    }


    private static void serializeLoginRequest(Document document, Element rootElement,
                                              ClientMessage.LoginRequest loginRequest) {
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(loginRequest.getUserName());
        rootElement.appendChild(nameElement);
    }

    private static void serializeListMembers(Document document, Element rootElement,
                                             ClientMessage.ListMembers listMembers) {
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(listMembers.getSessionID()));
        rootElement.appendChild(sessionElement);
    }

    private static void serializeMessage(Document document, Element rootElement,
                                         ClientMessage.Message message) {
        Element messageElement = document.createElement("message");
        messageElement.setTextContent(message.getMessageText());
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(message.getSessionID()));
        rootElement.appendChild(messageElement);
        rootElement.appendChild(sessionElement);
    }

    private static void serializeLogout(Document document, Element rootElement,
                                        ClientMessage.Logout logout) {
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(logout.getSessionID()));
        rootElement.appendChild(sessionElement);
    }


    public static ClientMessage deserialize(byte[] input) throws IOException, ClassNotFoundException {
        try {


            switch (System.getProperty("PROTOCOL")) {
                case "SERIALIZATION" -> {
                    var in = new ByteArrayInputStream(input);
                    var inputStream = new ObjectInputStream(in);
                    return (ClientMessage) inputStream.readObject();
                }
                case "XML" -> {
                    return deserializeXml(input);
                }

                default -> throw new RuntimeException("invalid protocol");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ClientMessage deserializeXml(byte[] bytes) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // Create an InputSource from the XML string
        InputSource inputSource = new InputSource(new ByteArrayInputStream(bytes));

        Document document = documentBuilder.parse(inputSource);

        Element rootElement = document.getDocumentElement();
        String commandName = rootElement.getAttribute("name");

        switch (commandName) {
            case "login" -> {
                Element nameElement = (Element) rootElement.getElementsByTagName("name").item(0);
                String userName = nameElement.getTextContent();
                return new ClientMessage.LoginRequest(userName);
            }
            case "list" -> {
                Element sessionElement = (Element) rootElement.getElementsByTagName("session").item(0);
                int sessionID = Integer.parseInt(sessionElement.getTextContent());
                return new ClientMessage.ListMembers(sessionID);
            }
            case "message" -> {
                Element messageElement = (Element) rootElement.getElementsByTagName("message").item(0);
                String messageText = messageElement.getTextContent();
                Element sessionElement = (Element) rootElement.getElementsByTagName("session").item(0);
                int sessionID = Integer.parseInt(sessionElement.getTextContent());
                return new ClientMessage.Message(sessionID, messageText);
            }
            case "logout" -> {
                Element sessionElement = (Element) rootElement.getElementsByTagName("session").item(0);
                int sessionID = Integer.parseInt(sessionElement.getTextContent());
                return new ClientMessage.Logout(sessionID);
            }
            default -> throw new IllegalArgumentException("Invalid XML structure.");
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