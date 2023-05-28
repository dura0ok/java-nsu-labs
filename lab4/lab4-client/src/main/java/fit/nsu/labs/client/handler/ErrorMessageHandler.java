package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.MainWindow;
import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.User;
import fit.nsu.labs.common.message.server.ErrorMessage;
import fit.nsu.labs.common.message.server.SrvListMembers;

import java.util.Collections;

public class ErrorMessageHandler  implements MessageHandler<ErrorMessage> {
    private final MainWindow mainWindow;
    public ErrorMessageHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void handle(ErrorMessage message, ServerConnection connection) {
        System.out.println("[DEBUG] [handler.ErrorMessage]");
        mainWindow.notification(new Event(Event.EventType.ERROR_MESSAGE, Collections.singletonList(message.getMessage())));
    }
}
