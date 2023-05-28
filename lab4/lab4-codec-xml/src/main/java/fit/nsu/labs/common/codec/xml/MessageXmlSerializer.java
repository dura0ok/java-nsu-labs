package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.client.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MessageXmlSerializer implements XmlSerializer<Message> {

    @Override
    public void serialize(Message message, Document document, Element rootElement) {
        Element messageElement = document.createElement("message");
        messageElement.setTextContent(message.getMessageText());
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(message.getSessionID()));
        rootElement.appendChild(messageElement);
        rootElement.appendChild(sessionElement);
    }

    @Override
    public Message deserialize(Class<Message> clazz, Element rootElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            Element messageElement = (Element) rootElement.getElementsByTagName("message").item(0);
            message.setMessageText(messageElement.getTextContent());

            Element sessionElement = (Element) rootElement.getElementsByTagName("session").item(0);
            message.setSessionID(Integer.parseInt(sessionElement.getTextContent()));

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
