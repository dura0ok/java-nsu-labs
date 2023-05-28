package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.message.client.ListMembers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ListMembersXmlSerializer implements XmlSerializer<ListMembers> {

    @Override
    public void serialize(ListMembers message, Document document, Element rootElement) {
        Element sessionElement = document.createElement("session");
        sessionElement.setTextContent(String.valueOf(message.getSessionID()));
        rootElement.appendChild(sessionElement);
    }

    @Override
    public ListMembers deserialize(Class<ListMembers> clazz, Element rootElement) {
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
