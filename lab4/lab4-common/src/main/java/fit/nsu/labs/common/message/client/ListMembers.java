package fit.nsu.labs.common.message.client;

import java.util.Objects;

public class ListMembers extends ClientMessage {
    private int sessionID;

    public ListMembers() {
        super(Type.COMMAND, "list");
    }

    public ListMembers(int sessionID) {
        this();
        setSessionID(sessionID);
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMembers that = (ListMembers) o;
        return getSessionID() == that.getSessionID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionID());
    }
}
