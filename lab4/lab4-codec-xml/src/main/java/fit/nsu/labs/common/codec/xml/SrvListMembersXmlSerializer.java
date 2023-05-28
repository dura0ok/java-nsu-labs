package fit.nsu.labs.common.codec.xml;

import fit.nsu.labs.common.User;
import fit.nsu.labs.common.message.server.SrvListMembers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class SrvListMembersXmlSerializer implements XmlSerializer<SrvListMembers> {
    @Override
    public void serialize(SrvListMembers message, Document document, Element rootElement) {
        Element listUsersElement = document.createElement("listusers");

        for (User user : message.getUserList()) {
            Element userElement = document.createElement("user");
            Element nameElement = document.createElement("name");
            nameElement.setTextContent(user.name());
            Element typeElement = document.createElement("type");
            typeElement.setTextContent(String.valueOf(user.sessionID()));

            userElement.appendChild(nameElement);
            userElement.appendChild(typeElement);
            listUsersElement.appendChild(userElement);
        }

        rootElement.appendChild(listUsersElement);
    }

    @Override
    public SrvListMembers deserialize(Class<SrvListMembers> clazz, Element childElement) {
        try {
            var message = clazz.getDeclaredConstructor().newInstance();
            NodeList userNodes = childElement.getElementsByTagName("user");
            List<User> userList = new ArrayList<>(userNodes.getLength());

            for (int i = 0; i < userNodes.getLength(); i++) {
                Element userElement = (Element) userNodes.item(i);
                String name = userElement.getElementsByTagName("name").item(0).getTextContent();
                Integer sessionID = Integer.parseInt(userElement.getElementsByTagName("type").item(0).getTextContent());

                userList.add(new User(name, sessionID));
            }

            message.setUserList(userList);

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
