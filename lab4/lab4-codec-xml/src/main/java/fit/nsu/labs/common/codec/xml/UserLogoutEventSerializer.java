package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.server.UserLogoutEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UserLogoutEventSerializer implements XmlSerializer<UserLogoutEvent> {

    @Override
    public void serialize(UserLogoutEvent message, Document document, Element rootElement) {
        rootElement.setAttribute("name", "userlogout");

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(message.getUserName());

        rootElement.appendChild(nameElement);
    }

    @Override
    public UserLogoutEvent deserialize(Class<UserLogoutEvent> clazz, Element childElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            Element nameElem = (Element) childElement.getElementsByTagName("name").item(0);
            String name = nameElem.getTextContent();

            message.setUserName(name);

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
