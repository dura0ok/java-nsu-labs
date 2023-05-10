package fit.nsu.labs.client.model.handlers.serialization;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.net.Socket;

import static fit.nsu.labs.Utils.serializeMessage;

public class Output implements Runnable {
    private final Socket cientSocket;
    private final StaticOutput<ClientMessage> notifier = new StaticOutput<>();

    public Output(Socket cientSocket) {
        this.cientSocket = cientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                var res = notifier.getOutput(cientSocket);
                sendMessage(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(ClientMessage res) throws IOException {
        byte[] serializedObject = serializeMessage(res);
        cientSocket.getOutputStream().write(serializedObject);
        cientSocket.getOutputStream().flush();
    }

    public StaticOutput<ClientMessage> getNotifier() {
        return notifier;
    }
}
