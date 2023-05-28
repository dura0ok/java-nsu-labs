package fit.nsu.labs.client;

import fit.nsu.labs.common.message.server.ServerMessage;

public record IncomingQueueItem(ServerMessage message, ServerConnection connection) {
}
