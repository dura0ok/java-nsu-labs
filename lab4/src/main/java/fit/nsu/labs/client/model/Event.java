package fit.nsu.labs.client.model;

import java.util.List;

public record Event(EventType type, List<String> data) {
    public enum EventType {
        MEMBERS_UPDATED,
        MESSAGE_UPDATED
    }
}