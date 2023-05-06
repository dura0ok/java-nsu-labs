package fit.nsu.labs.client;

import fit.nsu.labs.Configuration;

import java.io.*;
import java.net.Socket;

public class SocketConnection implements Connection {
    private final Socket clientSocket;
    private final Configuration configuration = new Configuration();
    private final OutputStream outToServer;
    private final InputStream inFromServer;

    SocketConnection() throws IOException {
        clientSocket = new Socket(configuration.getServerName(), configuration.getPort());
        outToServer = clientSocket.getOutputStream();
        inFromServer = clientSocket.getInputStream();

        Thread inputThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inFromServer));
            try {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    System.out.println("Server: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inputThread.start();

    }

    public void sendMessage(byte[] data) throws IOException {
        outToServer.write(data);
        outToServer.flush();
    }
}
