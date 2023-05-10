package fit.nsu.labs.server.protocol;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.net.Socket;
import java.util.Set;

public class XMLOutput extends OutputHandler {
    private final Set<Socket> connectedClients;
    private final StaticOutput<ServerMessage> notifier;

    public XMLOutput(Socket clientSocket, Set<Socket> connectedClients, StaticOutput<ServerMessage> notifier) {
        super(clientSocket);
        this.connectedClients = connectedClients;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            while (true) {
                var res = notifier.getOutput(getClientSocket());
                System.out.println(res);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
