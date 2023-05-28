package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.server.LoginResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LoginResponseXmlSerializer implements XmlSerializer<LoginResponse> {

    @Override
    public void serialize(LoginResponse message, Document document, Element rootElement) {
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(message.getSessionID()));
        rootElement.appendChild(sessionElement);
    }

    @Override
    public LoginResponse deserialize(Class<LoginResponse> clazz, Element childElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            message.setSessionID(Integer.parseInt(childElement.getTextContent()));
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
