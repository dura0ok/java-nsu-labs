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


    private void acceptNewConnections(){
        System.out.println("SERVER PORT " + configuration.getPort());
        while(true){
            try{
                var socket = serverSocket.accept();
                new Thread(new InputHandler(socket)).start();
                new Thread(new OutputHandler(socket)).start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
