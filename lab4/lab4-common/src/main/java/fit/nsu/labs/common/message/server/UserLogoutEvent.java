package fit.nsu.labs.common.message.server;

public class UserLogoutEvent extends ServerMessage {
    private String userName;

    public UserLogoutEvent() {
        super(ErrorType.EVENT);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
