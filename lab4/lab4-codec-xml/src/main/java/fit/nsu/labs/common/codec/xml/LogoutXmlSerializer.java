package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.client.Logout;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogoutXmlSerializer implements XmlSerializer<Logout> {

    @Override
    public void serialize(Logout message, Document document, Element rootElement) {
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(message.getSessionID()));
        rootElement.appendChild(sessionElement);
    }

    @Override
    public Logout deserialize(Class<Logout> clazz, Element rootElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            Element sessionElement = (Element) rootElement.getElementsByTagName("session").item(0);
            message.setSessionID(Integer.parseInt(sessionElement.getTextContent()));

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
