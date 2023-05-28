package fit.nsu.labs.common.message.server;

import fit.nsu.labs.common.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SrvListMembers extends ServerMessage {
    private List<User> userList;

    public SrvListMembers() {
        super(ErrorType.SUCCESS);
    }

    public SrvListMembers(List<User> userList) {
        this();
        setUserList(userList);
    }

    public List<User> getUserList() {
        return userList == null ? Collections.emptyList() : userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SrvListMembers that = (SrvListMembers) o;
        return Objects.equals(getUserList(), that.getUserList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserList());
    }
}
