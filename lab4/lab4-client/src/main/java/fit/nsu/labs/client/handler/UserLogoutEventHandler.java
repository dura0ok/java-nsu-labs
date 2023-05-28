package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.MainWindow;
import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.message.server.UserLogoutEvent;

import java.util.List;

public class UserLogoutEventHandler implements MessageHandler<UserLogoutEvent> {
    private final MainWindow mainWindow;

    public UserLogoutEventHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void handle(UserLogoutEvent message, ServerConnection connection) {
        mainWindow.notification(new Event(Event.EventType.MESSAGE_UPDATED,
                List.of("<%s leave server>".formatted(message.getUserName()))));
    }
}
