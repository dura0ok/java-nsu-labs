package fit.nsu.labs.client.model.handlers;

import fit.nsu.labs.client.model.ChatClientModel;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputXML extends InputHandler implements Runnable {
    private final StaticOutput<ClientMessage> notifier;
    private final Socket clientSocket;
    private final ChatClientModel model;

    public InputXML(Socket clientSocket, ChatClientModel model, StaticOutput<ClientMessage> notifier) throws IOException {
        this.clientSocket = clientSocket;
        this.model = model;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            try (var stream = new DataInputStream(clientSocket.getInputStream())) {
                while (!Thread.interrupted()) {
                    var length = stream.readInt();
                    var xmlBytes = stream.readNBytes(length);
                    var msg = ServerMessage.deserialize(xmlBytes);
                    handle(msg, model);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
