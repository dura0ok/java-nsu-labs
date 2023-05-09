package fit.nsu.labs.server.protocol;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.TextMessage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

abstract public class InputHandler implements Runnable {
    protected final Socket clientSocket;
    protected final StaticOutput<ServerMessage> notifier;
    protected static final List<TextMessage> messages = new ArrayList<>();
    InputHandler(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        this.clientSocket = clientSocket;
        this.notifier = notifier;
    }
}
