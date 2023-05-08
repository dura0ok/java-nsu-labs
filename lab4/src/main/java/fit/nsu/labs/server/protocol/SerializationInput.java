package fit.nsu.labs.server.protocol;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.server.StaticOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class SerializationInput extends InputHandler {
    private final static AtomicInteger sessionID = new AtomicInteger(1);

    public SerializationInput(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ClientMessage inputObject = (ClientMessage) objectInputStream.readObject();
            var res = handleMessage(inputObject);
            System.out.println(res);
            StaticOutput.notifyOutput(clientSocket, res);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerMessage handleMessage(ClientMessage message) {
        switch (message.type()) {
            case LOGIN -> {
                return handleLogin(message);
            }
            case MESSAGE -> {
                return new ServerMessage(ServerMessage.Error.ERROR, ServerMessage.Type.LOGIN_RESPONSE, "asd");
            }
            default -> throw new RuntimeException("Strange message");
        }
    }

    private ServerMessage handleLogin(ClientMessage message) {
        System.out.println("HANDLE LOGIN");
        return new ServerMessage(ServerMessage.Error.SUCCESS, ServerMessage.Type.LOGIN_RESPONSE, String.valueOf(sessionID.getAndIncrement()));
    }

    private void handleNewMessage(ClientMessage message) {
        System.out.println(message);
    }


}

