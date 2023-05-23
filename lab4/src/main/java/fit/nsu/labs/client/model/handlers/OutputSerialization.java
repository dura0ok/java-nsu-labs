package fit.nsu.labs.client.model.handlers;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.ObjectOutputStream;
import java.net.Socket;


public class OutputSerialization implements Runnable {
    private final Socket clientSocket;
    private final StaticOutput<ClientMessage> notifier;

    public OutputSerialization(Socket cientSocket, StaticOutput<ClientMessage> notifier) {
        this.clientSocket = cientSocket;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try (var output = new ObjectOutputStream(clientSocket.getOutputStream())) {
            while (true) {

                var res = notifier.getOutput(clientSocket);
                output.writeObject(res);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
