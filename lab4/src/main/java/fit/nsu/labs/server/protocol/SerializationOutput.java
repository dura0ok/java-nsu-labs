package fit.nsu.labs.server.protocol;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

import static fit.nsu.labs.Utils.serializeMessage;

public class SerializationOutput extends OutputHandler {
    private final Set<Socket> connectedClients;
    private final StaticOutput<ServerMessage> notifier;

    public SerializationOutput(Socket clientSocket, Set<Socket> connectedClients, StaticOutput<ServerMessage> notifier) {
        super(clientSocket);
        this.connectedClients = connectedClients;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            while (true) {
                var res = notifier.getOutput(getClientSocket());
                System.out.println("!!!!!!!!!!!!!!!!! Output " + res);
                getClientSocket().getOutputStream().write(serializeMessage(res));
                getClientSocket().getOutputStream().flush();
                System.out.println(connectedClients);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public StaticOutput<ServerMessage> getNotifier() {
        return notifier;
    }

}
