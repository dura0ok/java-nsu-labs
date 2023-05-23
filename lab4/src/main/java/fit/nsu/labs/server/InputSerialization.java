package fit.nsu.labs.server;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.User;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InputSerialization extends InputHandler {
    private final static AtomicInteger sessionID = new AtomicInteger(1);
    private static final Map<Socket, User> clients = new HashMap<>();

    public InputSerialization(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        super(clientSocket, notifier);
    }


    @Override
    public void run() {
        try (var inp = new ObjectInputStream(clientSocket.getInputStream())) {
            while (true) {
                handleMessage((ClientMessage) inp.readObject());
            }
        }catch (EOFException e){
            return;
        }
        catch (Exception e) {
                    throw new RuntimeException(e);
        }

    }

}

