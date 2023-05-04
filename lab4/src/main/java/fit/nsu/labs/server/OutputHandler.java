package fit.nsu.labs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class OutputHandler implements Runnable {
    private final Socket clientSocket;

    public OutputHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
