package fit.nsu.labs.common.message.server;

public class UserLoginEvent extends ServerMessage {
    private String userName;

    public UserLoginEvent() {
        super(ErrorType.EVENT);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
