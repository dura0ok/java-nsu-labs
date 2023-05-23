package fit.nsu.labs.client.model.handlers;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.DataOutputStream;
import java.net.Socket;


public class OutputXML implements Runnable {
    private final Socket clientSocket;
    private final StaticOutput<ClientMessage> notifier;

    public OutputXML(Socket cientSocket, StaticOutput<ClientMessage> notifier) {
        this.clientSocket = cientSocket;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try (var stream = new DataOutputStream(clientSocket.getOutputStream());) {
            while (true) {
                var res = notifier.getOutput(clientSocket);
                var serialized = ClientMessage.serialize(res);
                stream.writeInt(serialized.length);
                stream.write(serialized);
                stream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
