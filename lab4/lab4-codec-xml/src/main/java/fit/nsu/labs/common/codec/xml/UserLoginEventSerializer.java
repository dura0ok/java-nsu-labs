package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.server.UserLoginEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UserLoginEventSerializer implements XmlSerializer<UserLoginEvent> {

    @Override
    public void serialize(UserLoginEvent message, Document document, Element rootElement) {
        rootElement.setAttribute("name", "userlogin");

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(message.getUserName());

        rootElement.appendChild(nameElement);
    }

    @Override
    public UserLoginEvent deserialize(Class<UserLoginEvent> clazz, Element childElement) {
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
