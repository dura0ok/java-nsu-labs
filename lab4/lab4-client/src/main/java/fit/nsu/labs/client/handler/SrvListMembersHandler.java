package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.MainWindow;
import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.User;
import fit.nsu.labs.common.message.server.SrvListMembers;

public class SrvListMembersHandler implements MessageHandler<SrvListMembers> {
    private final MainWindow mainWindow;

    public SrvListMembersHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void handle(SrvListMembers message, ServerConnection connection) {
        System.out.println("[DEBUG] [handler.SrvListMembers]");

        mainWindow.notification(new Event(Event.EventType.MEMBERS_UPDATED,
                message.getUserList().stream()
                        .map(User::name)
                        .toList()));
    }
}
