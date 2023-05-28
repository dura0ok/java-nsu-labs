package fit.nsu.labs.common.message.client;

import java.util.Objects;

public class LoginRequest extends ClientMessage {
    private String userName;

    public LoginRequest() {
        super(Type.COMMAND, "login");
    }

    public LoginRequest(String userName) {
        this();
        setUserName(userName);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(getUserName(), that.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }
}
