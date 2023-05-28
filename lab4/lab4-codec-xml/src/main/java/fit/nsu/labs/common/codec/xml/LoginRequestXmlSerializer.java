package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.client.LoginRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LoginRequestXmlSerializer implements XmlSerializer<LoginRequest> {

    @Override
    public void serialize(LoginRequest message, Document document, Element rootElement) {
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(message.getUserName());
        rootElement.appendChild(nameElement);
    }

    @Override
    public LoginRequest deserialize(Class<LoginRequest> clazz, Element rootElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            Element nameElement = (Element) rootElement.getElementsByTagName("name").item(0);
            message.setUserName(nameElement.getTextContent());

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
