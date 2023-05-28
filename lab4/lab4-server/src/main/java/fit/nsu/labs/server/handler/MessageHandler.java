package fit.nsu.labs.server.handler;

import fit.nsu.labs.common.message.client.ClientMessage;
import fit.nsu.labs.server.ClientConnection;

public interface MessageHandler<T extends ClientMessage> {
    void handle(T message, ClientConnection connection);
}
