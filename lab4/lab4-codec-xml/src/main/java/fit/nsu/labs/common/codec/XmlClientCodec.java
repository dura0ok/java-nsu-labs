package fit.nsu.labs.common.codec;

import fit.nsu.labs.common.codec.xml.ListMembersXmlSerializer;
import fit.nsu.labs.common.codec.xml.LoginRequestXmlSerializer;
import fit.nsu.labs.common.codec.xml.LogoutXmlSerializer;
import fit.nsu.labs.common.codec.xml.MessageXmlSerializer;
import fit.nsu.labs.common.exception.DecoderException;
import fit.nsu.labs.common.exception.EncoderException;
import fit.nsu.labs.common.message.client.*;

import java.io.InputStream;

public class XmlClientCodec extends AbstractXmlCodec implements Encoder<ClientMessage>, Decoder<ClientMessage> {
    private final ListMembersXmlSerializer listMembersXmlSerializer = new ListMembersXmlSerializer();
    private final LoginRequestXmlSerializer loginRequestXmlSerializer = new LoginRequestXmlSerializer();
    private final MessageXmlSerializer messageXmlSerializer = new MessageXmlSerializer();
    private final LogoutXmlSerializer logoutXmlSerializer = new LogoutXmlSerializer();

    @Override
    public byte[] encode(ClientMessage message) throws EncoderException {
        System.out.printf("[DEBUG] [client.codec] outgoing message %s\n", message.getClass().getSimpleName());

        try {
            var document = createDocument();

            var rootElement = document.createElement("command");
            rootElement.setAttribute("name", message.getName());

            if (message.getClass().equals(ListMembers.class)) {
                listMembersXmlSerializer.serialize((ListMembers) message, document, rootElement);
            } else if (message.getClass().equals(LoginRequest.class)) {
                loginRequestXmlSerializer.serialize((LoginRequest) message, document, rootElement);
            } else if (message.getClass().equals(Message.class)) {
                messageXmlSerializer.serialize((Message) message, document, rootElement);
            } else if (message.getClass().equals(Logout.class)) {
                logoutXmlSerializer.serialize((Logout) message, document, rootElement);
            }

            document.appendChild(rootElement);

            return toBytes(document);
        } catch (Exception e) {
            throw new EncoderException(e);
        }
    }

    @Override
    public ClientMessage decode(InputStream inputStream) throws DecoderException {
        try {
            var document = parseFromStream(inputStream);

            var rootElement = document.getDocumentElement();
            var commandName = rootElement.getAttribute("name");

            ClientMessage result = switch (commandName) {
                case "list" -> listMembersXmlSerializer.deserialize(ListMembers.class, rootElement);
                case "login" -> loginRequestXmlSerializer.deserialize(LoginRequest.class, rootElement);
                case "message" -> messageXmlSerializer.deserialize(Message.class, rootElement);
                case "logout" -> logoutXmlSerializer.deserialize(Logout.class, rootElement);
                default -> throw new IllegalArgumentException("Invalid XML structure.");
            };

            System.out.printf("[DEBUG] [client.codec] input message %s\n", result.getClass().getSimpleName());
            return result;
        } catch (Exception e) {
            throw new DecoderException(e);
        }
    }
}
