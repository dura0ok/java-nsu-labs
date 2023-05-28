package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.common.message.server.ServerMessage;

public interface MessageHandler<T extends ServerMessage> {
    void handle(T message, ServerConnection connection);
}
