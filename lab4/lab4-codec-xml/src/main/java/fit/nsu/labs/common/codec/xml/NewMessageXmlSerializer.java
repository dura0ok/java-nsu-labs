package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.TextMessage;
import fit.nsu.labs.common.message.server.NewMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NewMessageXmlSerializer implements XmlSerializer<NewMessage> {

    @Override
    public void serialize(NewMessage message, Document document, Element rootElement) {
        rootElement.setAttribute("name", "message");

        Element messageElement = document.createElement("message");
        messageElement.setTextContent(message.getMessage().text());
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(message.getMessage().name());

        rootElement.appendChild(messageElement);
        rootElement.appendChild(nameElement);
    }

    @Override
    public NewMessage deserialize(Class<NewMessage> clazz, Element childElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            Element messageElem = (Element) childElement.getElementsByTagName("message").item(0);
            String msg = messageElem.getTextContent();
            Element nameElem = (Element) childElement.getElementsByTagName("name").item(0);
            String name = nameElem.getTextContent();

            message.setMessage(new TextMessage(name, msg));

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
