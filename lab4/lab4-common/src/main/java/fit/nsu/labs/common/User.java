package fit.nsu.labs.common;

import java.io.Serializable;

public record User(String name, Integer sessionID) implements Serializable {
}
