package fit.nsu.labs.server;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.TextMessage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

abstract public class InputHandler implements Runnable {
    protected static final List<TextMessage> messages = new ArrayList<>();
    protected final Socket clientSocket;
    protected final StaticOutput<ServerMessage> notifier;

    public InputHandler(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        this.clientSocket = clientSocket;
        this.notifier = notifier;
    }

    protected List<TextMessage> getLastNMessages(int n) {
        List<Integer> res;
        if (n > messages.size()) {
            return messages;
        }
        return messages.subList(messages.size() - n, messages.size());
    }

    protected ServerMessage generateNewMessageResponse(TextMessage message) {
        return new ServerMessage.NewMessage(message);
    }
}
