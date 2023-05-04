package fit.nsu.labs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class InputHandler implements Runnable {
    private Socket clientSocket;

    public InputHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Handle input from client here
                System.out.println(inputLine);
            }
            System.out.println("closing");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
