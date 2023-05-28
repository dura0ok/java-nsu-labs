package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.server.ErrorMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ErrorMessageXmlSerializer implements XmlSerializer<ErrorMessage> {

    @Override
    public void serialize(ErrorMessage message, Document document, Element rootElement) {
        Element messageElement = document.createElement("message");
        messageElement.setTextContent(message.getMessage());
        rootElement.appendChild(messageElement);
    }

    @Override
    public ErrorMessage deserialize(Class<ErrorMessage> clazz, Element rootElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            Element messageElement = (Element) rootElement.getElementsByTagName("message").item(0);
            message.setMessage(messageElement.getTextContent());

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
