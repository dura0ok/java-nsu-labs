package fit.nsu.labs.server;

import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.util.Set;


public class OutputXML extends OutputHandler {
    private final Set<Socket> connectedClients;
    private final StaticOutput<ServerMessage> notifier;

    public OutputXML(Socket clientSocket, Set<Socket> connectedClients, StaticOutput<ServerMessage> notifier) {
        super(clientSocket);
        this.connectedClients = connectedClients;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try (var stream = new DataOutputStream(getClientSocket().getOutputStream())) {
            while (true) {
                var out = notifier.getOutput(getClientSocket());
                var res = ServerMessage.serialize(out);
                stream.writeInt(res.length);
                stream.write(res);
                stream.flush();
            }
        }catch (EOFException e){
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
