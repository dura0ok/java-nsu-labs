package fit.nsu.labs.server.handler;

import fit.nsu.labs.common.message.client.ListMembers;
import fit.nsu.labs.common.message.server.SrvListMembers;
import fit.nsu.labs.server.ClientConnection;
import fit.nsu.labs.server.UserManager;

public class ListMembersHandler implements MessageHandler<ListMembers>{
    private final UserManager userManager;

    public ListMembersHandler(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void handle(ListMembers message, ClientConnection connection) {
        System.out.println("[DEBUG] [handler.ListMembers]");
        var srvListMembers = new SrvListMembers();
        srvListMembers.setUserList(userManager.listUsers().stream()
                .map(u -> new fit.nsu.labs.common.User(u.name(), connection.getSessionId()))
                .toList());

        connection.send(srvListMembers);
    }
}
