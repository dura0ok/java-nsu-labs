package fit.nsu.labs.server;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.server.protocol.SerializationInput;
import fit.nsu.labs.server.protocol.SerializationOutput;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class IncomeConnectionEndpoint {
    private final Configuration configuration;
    private final ServerSocket serverSocket;
    private final Set<Socket> connectedClients = new HashSet<>();


    public IncomeConnectionEndpoint() throws IOException {
        this.configuration = new Configuration();
        serverSocket = new ServerSocket(configuration.getPort());
    }

    public static void main(String[] args) throws IOException {
        var server = new IncomeConnectionEndpoint();
        server.start();
    }

    private void start() {
        System.out.println("SERVER PORT " + configuration.getPort());
        var notifier = new StaticOutput<ServerMessage>();
        while (true) {
            try {
                var socket = serverSocket.accept();
                connectedClients.add(socket);
                System.out.println("Accept new client " + socket);
                if (System.getProperty("PROTOCOL").equals("SERIALIZATION")) {
                    var output = new SerializationOutput(socket, connectedClients, notifier);
                    new Thread(output).start();
                    new Thread(new SerializationInput(socket, notifier)).start();

                }
//                if (System.getProperty("PROTOCOL").equals("XML")) {
//                    var output = new XMLOutput(socket, connectedClients, notifier);
//                    new Thread(output).start();
//                    new Thread(new XMLInput(socket, notifier)).start();
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
