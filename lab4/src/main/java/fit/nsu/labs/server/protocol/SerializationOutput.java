package fit.nsu.labs.server.protocol;

import fit.nsu.labs.server.StaticOutput;

import java.io.IOException;
import java.net.Socket;
import java.util.Set;

import static fit.nsu.labs.Utils.serializeMessage;

public class SerializationOutput extends OutputHandler {
    private final Set<Socket> connectedClients;
    public SerializationOutput(Socket clientSocket, Set<Socket> connectedClients) {
        super(clientSocket);
        this.connectedClients = connectedClients;
    }

    @Override
    public void run() {
        try {
            while (true) {
                var res = StaticOutput.getOutput(getClientSocket());
                System.out.println(res);
                getClientSocket().getOutputStream().write(serializeMessage(res));
                getClientSocket().getOutputStream().flush();
                System.out.println(connectedClients);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
