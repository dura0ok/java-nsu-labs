package fit.nsu.labs.common.message.client;

import java.util.Objects;

public class Logout extends ClientMessage {
    private int sessionID;

    public Logout() {
        super(Type.COMMAND, "logout");
    }

    public Logout(int sessionID) {
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
        Logout logout = (Logout) o;
        return getSessionID() == logout.getSessionID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionID());
    }
}
