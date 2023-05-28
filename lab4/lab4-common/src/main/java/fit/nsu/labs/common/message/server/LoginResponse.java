package fit.nsu.labs.common.message.server;

import java.util.Objects;

public class LoginResponse extends ServerMessage {
    private int sessionID;

    public LoginResponse() {
        super(ErrorType.SUCCESS);
    }

    public LoginResponse(int sessionID) {
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
        LoginResponse that = (LoginResponse) o;
        return getSessionID() == that.getSessionID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionID());
    }
}
