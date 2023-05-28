package fit.nsu.labs.server;

import fit.nsu.labs.common.message.client.ClientMessage;

public record IncomingQueueItem(ClientMessage message, ClientConnection connection) {
}
