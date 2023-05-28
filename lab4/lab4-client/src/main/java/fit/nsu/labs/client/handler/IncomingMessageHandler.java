package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.MainWindow;
import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.message.server.NewMessage;

import java.util.List;

public class IncomingMessageHandler implements MessageHandler<NewMessage> {
    private final MainWindow mainWindow;

    public IncomingMessageHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void handle(NewMessage message, ServerConnection connection) {
        var textMessage = message.getMessage();
        mainWindow.notification(new Event(Event.EventType.MESSAGE_UPDATED, List.of(textMessage.toString())));
    }
}
