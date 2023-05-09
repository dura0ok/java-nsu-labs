package fit.nsu.labs.common;

import java.io.Serializable;
import java.util.List;

public record ServerMessage(Error error, Type eventName, List<String> data) implements Serializable {
    public enum Error {
        ERROR, SUCCESS
    }

    public enum Type {
        LOGIN_RESPONSE,
        MEMBERS_LIST_UPDATED,
        MESSAGE_LIST_UPDATED
    }

}