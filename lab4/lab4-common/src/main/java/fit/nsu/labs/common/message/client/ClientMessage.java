package fit.nsu.labs.common.message.client;

import java.io.Serializable;

public abstract class ClientMessage implements Serializable {
    private final Type type;
    private final String name;

    public ClientMessage(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public enum Type {
        COMMAND,
        EVENT
    }
}
