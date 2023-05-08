package fit.nsu.labs.common;

import java.io.Serializable;

public record ServerMessage(Error error, Type eventName, String Data) implements Serializable {
    public enum Error {
        ERROR, SUCCESS
    }

    public enum Type {
        LOGIN_RESPONSE,
        MEMBERS_LIST_UPDATED
    }

}