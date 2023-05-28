package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.MainWindow;
import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.message.server.UserLoginEvent;

import java.util.List;

public class UserLoginEventHandler implements MessageHandler<UserLoginEvent> {
    private final MainWindow mainWindow;

    public UserLoginEventHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void handle(UserLoginEvent message, ServerConnection connection) {
        mainWindow.notification(new Event(Event.EventType.MESSAGE_UPDATED,
                List.of("<%s join server>".formatted(message.getUserName()))));
    }
}
