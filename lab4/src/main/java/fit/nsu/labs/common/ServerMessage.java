package fit.nsu.labs.common;

import fit.nsu.labs.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract sealed class ServerMessage implements Serializable
        permits ServerMessage.EmptySuccess, ServerMessage.Error, ServerMessage.ListMembers,
        ServerMessage.ListMessages, ServerMessage.LoginResponse, ServerMessage.NewMessage {
    // Constants for XML tags and attributes
    private static final String TAG_ERROR = "error";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_LIST_USERS = "listusers";
    private static final String TAG_USER = "user";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_EVENT = "event";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SESSION = "session";
    private static final String ATTR_NAME = "name";
    private static final String EVENT_NAME_MESSAGE = "message";
    private final ErrorType errorType;

    protected ServerMessage(ErrorType errorType) {
        this.errorType = errorType;
    }

    public static ServerMessage deserialize(byte[] bytes) throws Exception {
        switch (System.getProperty("PROTOCOL")) {
            case "SERIALIZATION" -> {
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(in);
                ServerMessage obj = (ServerMessage) ois.readObject();
                ois.close();
                return obj;
            }
            case "XML" -> {
                return ServerMessage.deserializeXml(bytes);
            }

            default -> throw new RuntimeException("invalid protocol");
        }
    }

    public static byte[] serialize(ServerMessage out) {
        try {


            switch (System.getProperty("PROTOCOL")) {
                case "SERIALIZATION" -> {
                    return Utils.getBytes(out);
                }
                case "XML" -> {
                    return ServerMessage.serializeXML(out).getBytes();
                }

                default -> throw new RuntimeException("invalid protocol");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ServerMessage deserializeXml(byte[] bytes) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // Create an InputSource from the XML string
        InputSource inputSource = new InputSource(new ByteArrayInputStream(bytes));

        Document document = documentBuilder.parse(inputSource);

        Element rootElement = document.getDocumentElement();
        String rootTagName = rootElement.getTagName();

        return switch (rootTagName) {
            case TAG_ERROR -> deserializeError(rootElement);
            case TAG_SUCCESS -> deserializeSuccess(rootElement);
            default -> throw new IllegalArgumentException("Invalid XML structure.");
        };
    }

    public static String serializeXML(ServerMessage message) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element rootElement;
        if (message.getErrorType() == ServerMessage.ErrorType.ERROR) {
            rootElement = document.createElement(TAG_ERROR);
            serializeError(document, rootElement, (ServerMessage.Error) message);
        } else {
            rootElement = document.createElement(TAG_SUCCESS);

            if (message instanceof ServerMessage.ListMembers listMembers) {
                serializeListMembers(document, rootElement, listMembers);
            } else if (message instanceof ServerMessage.NewMessage newMessage) {
                serializeNewMessage(document, rootElement, newMessage

                );
            } else if (message instanceof ServerMessage.LoginResponse loginResponse) {
                serializeLoginResponse(document, rootElement, loginResponse);
            }
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

    private static void serializeListMembers(Document document, Element rootElement,
                                             ServerMessage.ListMembers listMembers) {
        Element listUsersElement = document.createElement(TAG_LIST_USERS);
        List<User> userList = listMembers.getUserList();

        for (User user : userList) {
            Element userElement = document.createElement(TAG_USER);
            Element nameElement = document.createElement(TAG_NAME);
            nameElement.setTextContent(user.name());
            Element typeElement = document.createElement(TAG_TYPE);
            typeElement.setTextContent(String.valueOf(user.sessionID()));

            userElement.appendChild(nameElement);
            userElement.appendChild(typeElement);
            listUsersElement.appendChild(userElement);
        }

        rootElement.appendChild(listUsersElement);
    }

    private static void serializeNewMessage(Document document, Element rootElement,
                                            ServerMessage.NewMessage newMessage) {
        Element eventElement = document.createElement(TAG_EVENT);
        eventElement.setAttribute(ATTR_NAME, EVENT_NAME_MESSAGE);

        Element messageElement = document.createElement(TAG_MESSAGE);
        messageElement.setTextContent(newMessage.getMessage().text());
        Element nameElement = document.createElement(TAG_NAME);
        nameElement.setTextContent(newMessage.getMessage().name());

        eventElement.appendChild(messageElement);
        eventElement.appendChild(nameElement);
        rootElement.appendChild(eventElement);
    }

    private static void serializeLoginResponse(Document document, Element rootElement,
                                               ServerMessage.LoginResponse loginResponse) {
        Element sessionElement = document.createElement(TAG_SESSION);
        sessionElement.setTextContent(String.valueOf(loginResponse.getSessionID()));
        rootElement.appendChild(sessionElement);
    }

    private static void serializeError(Document document, Element rootElement, ServerMessage.Error errorMessage) {
        Element messageElement = document.createElement(TAG_MESSAGE);
        messageElement.setTextContent(errorMessage.getMessage());
        rootElement.appendChild(messageElement);
    }

    private static ServerMessage.ListMembers deserializeListUsers(Element childElement) {
        NodeList userNodes = childElement.getElementsByTagName(TAG_USER);
        List<User> userList = new ArrayList<>(userNodes.getLength());

        for (int i = 0; i < userNodes.getLength(); i++) {
            Element userElement = (Element) userNodes.item(i);
            String name = userElement.getElementsByTagName(TAG_NAME).item(0).getTextContent();
            Integer sessionID = Integer.parseInt(userElement.getElementsByTagName(TAG_TYPE).item(0).getTextContent());

            userList.add(new User(name, sessionID));
        }

        return new ServerMessage.ListMembers(userList);
    }

    private static ServerMessage.NewMessage deserializeEvent(Element childElement) {
        Element messageElem = (Element) childElement.getElementsByTagName(TAG_MESSAGE).item(0);
        String message = messageElem.getTextContent();
        Element nameElem = (Element) childElement.getElementsByTagName(TAG_NAME).item(0);
        String name = nameElem.getTextContent();

        return new ServerMessage.NewMessage(new TextMessage(name, message));
    }

    private static ServerMessage.LoginResponse deserializeSession(Element childElement) {
        String sessionID = childElement.getTextContent();
        return new ServerMessage.LoginResponse(Integer.parseInt(sessionID));
    }

    private static ServerMessage deserializeSuccess(Element rootElement) {
        NodeList nodeList = rootElement.getChildNodes();

        if (nodeList.getLength() != 1 || nodeList.item(0).getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("Invalid XML structure.");
        }

        Element childElement = (Element) nodeList.item(0);
        String childTagName = childElement.getTagName();

        return switch (childTagName) {
            case TAG_LIST_USERS -> deserializeListUsers(childElement);
            case TAG_EVENT -> deserializeEvent(childElement);
            case TAG_SESSION -> deserializeSession(childElement);
            default -> throw new IllegalStateException("Unexpected value: " + childTagName);
        };
    }

    private static ServerMessage deserializeError(Element rootElement) {
        Element messageElement = (Element) rootElement.getElementsByTagName(TAG_MESSAGE).item(0);
        String errorMessage = messageElement.getTextContent();
        return new ServerMessage.Error(errorMessage);
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
            return userList == null ? Collections.emptyList() : userList;
        }
    }

    public static final class ListMessages extends ServerMessage {
        private final List<TextMessage> messages;

        public ListMessages(List<TextMessage> messages) {
            super(ErrorType.SUCCESS);
            this.messages = messages;
        }

        public List<TextMessage> getMessages() {
            return messages == null ? Collections.emptyList() : messages;
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
