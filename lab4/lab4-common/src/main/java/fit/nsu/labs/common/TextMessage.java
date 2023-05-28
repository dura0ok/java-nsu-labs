package fit.nsu.labs.common;

import java.io.Serializable;

public record TextMessage(String name, String text) implements Serializable {
    @Override
    public String toString() {
        return name + ": " + text;
    }
}
