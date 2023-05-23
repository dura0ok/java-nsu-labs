package fit.nsu.labs.client.model.handlers;

import fit.nsu.labs.client.model.ChatClientModel;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class InputSerialization extends InputHandler implements Runnable {
    private final StaticOutput<ClientMessage> notifier;
    private final Socket clientSocket;
    private final ChatClientModel model;

    public InputSerialization(Socket clientSocket, ChatClientModel model, StaticOutput<ClientMessage> notifier) throws IOException {
        this.clientSocket = clientSocket;
        this.model = model;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try (var inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            while (!Thread.interrupted()) {
                handle((ServerMessage) inputStream.readObject(), model);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
