package fit.nsu.labs.server.handler;

import fit.nsu.labs.common.TextMessage;
import fit.nsu.labs.common.message.client.Message;
import fit.nsu.labs.common.message.server.NewMessage;
import fit.nsu.labs.server.ClientConnection;
import fit.nsu.labs.server.UserManager;

import java.util.concurrent.BlockingDeque;

public class IncomingMessageHandler implements MessageHandler<Message> {
    private final UserManager userManager;
    private final BlockingDeque<TextMessage> lastMessages;
    private final int countLastMessages;

    public IncomingMessageHandler(UserManager userManager, BlockingDeque<TextMessage> lastMessages, int countLastMessages) {
        this.userManager = userManager;
        this.lastMessages = lastMessages;
        this.countLastMessages = countLastMessages;
    }

    @Override
    public void handle(Message message, ClientConnection connection) {
        System.out.println("[DEBUG] [handler.Message]");
        var user = userManager.getBySessionId(connection.getSessionId())
                .orElseThrow(() -> new RuntimeException("ЧО? КУДА ПОЛЬЗОВАТЕЛЯ ДЕЛИ???"));

        var textMessage = new TextMessage(user.name(), message.getMessageText());
        try {
            if (lastMessages.size() == countLastMessages) lastMessages.removeFirst();
            lastMessages.putLast(textMessage);

            var newMessage = new NewMessage();
            newMessage.setMessage(textMessage);

            userManager.broadcastSend(newMessage);
        } catch (InterruptedException ignore) {
            // просто выходим
        }
    }
}
