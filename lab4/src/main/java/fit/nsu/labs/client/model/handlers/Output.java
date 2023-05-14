package fit.nsu.labs.client.model.handlers;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.net.Socket;


public class Output implements Runnable {
    private final Socket cientSocket;
    private final StaticOutput<ClientMessage> notifier;

    public Output(Socket cientSocket, StaticOutput<ClientMessage> notifier) {
        this.cientSocket = cientSocket;
        this.notifier = notifier;
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
        byte[] serializedObject = ClientMessage.serialize(res);
        cientSocket.getOutputStream().write(serializedObject);
        cientSocket.getOutputStream().flush();
    }
}
