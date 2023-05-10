package fit.nsu.labs.common;

import java.io.Serializable;
import java.util.List;

public record ServerMessage(Error error, Type eventName, List<String> data) implements Serializable {
    public static String serializeToXML(ServerMessage obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("<error>").append(obj.error().toString()).append("</error>");
        sb.append("<event_name>").append(obj.eventName().toString()).append("</event_name>");
        sb.append("<data>");
        for (String data : obj.data()) {
            sb.append("<item>").append(data).append("</item>");
        }
        sb.append("</data>");
        sb.append("</ServerMessage>");
        return sb.toString();
    }

    public enum Error {
        ERROR, SUCCESS
    }

    public enum Type {
        LOGIN_RESPONSE,
        MEMBERS_LIST_UPDATED,
        MESSAGE_LIST_UPDATED
    }
}