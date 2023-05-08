package fit.nsu.labs.server.protocol;

import java.net.Socket;

abstract public class InputHandler implements Runnable {
    protected final Socket clientSocket;

    InputHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
