package fit.nsu.labs.server.protocol;

import java.net.Socket;

public abstract class OutputHandler implements Runnable {

    private final Socket clientSocket;

    OutputHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
