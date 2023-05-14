package fit.nsu.labs.server;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;


public class Output extends OutputHandler {
    private final Set<Socket> connectedClients;
    private final StaticOutput<ServerMessage> notifier;

    public Output(Socket clientSocket, Set<Socket> connectedClients, StaticOutput<ServerMessage> notifier) {
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
                getClientSocket().getOutputStream().write(ServerMessage.serialize(res));
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
