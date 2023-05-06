package fit.nsu.labs.server;

import fit.nsu.labs.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

public class Entrypoint {
    private final Configuration configuration = new Configuration();
    private final ServerSocket serverSocket;

    public Entrypoint() throws IOException {
        serverSocket = new ServerSocket(configuration.getPort());
    }

    public static void main(String[] args) throws IOException {
        var server = new Entrypoint();
        server.acceptNewConnections();
    }

    private void acceptNewConnections() {
        System.out.println("SERVER PORT " + configuration.getPort());
        while (true) {
            try {
                var socket = serverSocket.accept();
                System.out.println("Accept new client " + socket);
                new Thread(new InputHandler(socket)).start();
                new Thread(new OutputHandler(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
