package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.codec.xml.*;
import fit.nsu.labs.common.exception.DecoderException;
import fit.nsu.labs.common.exception.EncoderException;
import fit.nsu.labs.common.message.server.*;
import org.w3c.dom.Element;

import java.io.InputStream;

public class XmlServerCodec extends AbstractXmlCodec implements Encoder<ServerMessage>, Decoder<ServerMessage> {
    private final ErrorMessageXmlSerializer errorMessageXmlSerializer = new ErrorMessageXmlSerializer();
    private final LoginResponseXmlSerializer loginResponseXmlSerializer = new LoginResponseXmlSerializer();
    private final SrvListMembersXmlSerializer listMembersXmlSerializer = new SrvListMembersXmlSerializer();
    private final NewMessageXmlSerializer messageXmlSerializer = new NewMessageXmlSerializer();
    private final UserLoginEventSerializer userLoginEventSerializer = new UserLoginEventSerializer();
    private final UserLogoutEventSerializer userLogoutEventSerializer = new UserLogoutEventSerializer();

    @Override
    public byte[] encode(ServerMessage message) throws EncoderException {
        System.out.printf("[DEBUG] [server.codec] outgoing message %s\n", message.getClass().getSimpleName());
        try {
            var document = createDocument();

            switch (message.getErrorType()) {
                case ERROR -> {
                    var rootElement = document.createElement("error");
                    errorMessageXmlSerializer.serialize((ErrorMessage) message, document, rootElement);
                    document.appendChild(rootElement);
                }
                case SUCCESS -> {
                    var rootElement = document.createElement("success");

                    if (message.getClass().equals(LoginResponse.class)) {
                        loginResponseXmlSerializer.serialize((LoginResponse) message, document, rootElement);
                    } else if (message.getClass().equals(SrvListMembers.class)) {
                        listMembersXmlSerializer.serialize((SrvListMembers) message, document, rootElement);
                    }

                    document.appendChild(rootElement);
                }
                case EVENT -> {
                    var rootElement = document.createElement("event");

                    if (message.getClass().equals(NewMessage.class)) {
                        messageXmlSerializer.serialize((NewMessage) message, document, rootElement);
                    } else if (message.getClass().equals(UserLoginEvent.class)) {
                        userLoginEventSerializer.serialize((UserLoginEvent) message, document, rootElement);
                    } else if (message.getClass().equals(UserLogoutEvent.class)) {
                        userLogoutEventSerializer.serialize((UserLogoutEvent) message, document, rootElement);
                    }

                    document.appendChild(rootElement);
                }
            }

            return toBytes(document);
        } catch (Exception e) {
            throw new EncoderException(e);
        }
    }

    @Override
    public ServerMessage decode(InputStream inputStream) throws DecoderException {
        try {
            var document = parseFromStream(inputStream);

            var rootElement = document.getDocumentElement();
            var rootTagName = rootElement.getTagName().toLowerCase();

            ServerMessage result;
            switch (rootTagName) {
                case "error" -> result = errorMessageXmlSerializer.deserialize(ErrorMessage.class, rootElement);
                case "success" -> {
                    var nodeList = rootElement.getChildNodes();

                    if (nodeList.getLength() != 1 || nodeList.item(0).getNodeType() != Element.ELEMENT_NODE) {
                        throw new IllegalArgumentException("Invalid XML structure.");
                    }

                    var childElement = (Element) nodeList.item(0);
                    var childTagName = childElement.getTagName().toLowerCase();

                    result = switch (childTagName) {
                        case "session" -> loginResponseXmlSerializer.deserialize(LoginResponse.class, childElement);
                        case "listusers" -> listMembersXmlSerializer.deserialize(SrvListMembers.class, childElement);
                        default -> throw new IllegalArgumentException("Invalid XML structure.");
                    };
                }
                case "event" -> {
                    var eventName = rootElement.getAttribute("name").toLowerCase();

                    result = switch (eventName) {
                        case "message" -> messageXmlSerializer.deserialize(NewMessage.class, rootElement);
                        case "userlogin" -> userLoginEventSerializer.deserialize(UserLoginEvent.class, rootElement);
                        case "userlogout" -> userLogoutEventSerializer.deserialize(UserLogoutEvent.class, rootElement);
                        default -> throw new IllegalArgumentException("Invalid XML structure.");
                    };
                }
                default -> throw new IllegalArgumentException("Invalid XML structure.");
            }

            System.out.printf("[DEBUG] [client.codec] input message %s\n", result.getClass().getSimpleName());
            return result;
        } catch (Exception e) {
            throw new DecoderException(e);
        }
    }
}
