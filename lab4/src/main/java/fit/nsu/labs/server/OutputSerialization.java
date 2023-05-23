package fit.nsu.labs.server;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;


public class OutputSerialization extends OutputHandler {
    private final Set<Socket> connectedClients;
    private final StaticOutput<ServerMessage> notifier;

    public OutputSerialization(Socket clientSocket, Set<Socket> connectedClients, StaticOutput<ServerMessage> notifier) {
        super(clientSocket);
        this.connectedClients = connectedClients;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try (var out = new ObjectOutputStream(getClientSocket().getOutputStream())) {
            while (!Thread.interrupted()) {
                var res = notifier.getOutput(getClientSocket());
                out.writeObject(res);
                out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
