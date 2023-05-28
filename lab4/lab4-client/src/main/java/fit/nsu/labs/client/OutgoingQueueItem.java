package fit.nsu.labs.client;

import fit.nsu.labs.common.message.client.ClientMessage;

public record OutgoingQueueItem(ClientMessage message, Runnable afterTask) {
}
