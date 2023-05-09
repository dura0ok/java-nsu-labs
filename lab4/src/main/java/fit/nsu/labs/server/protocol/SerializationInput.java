package fit.nsu.labs.server.protocol;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.TextMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class SerializationInput extends InputHandler {
    private final static AtomicInteger sessionID = new AtomicInteger(1);
    private static final Map<Socket, String> clients = new HashMap<>();



    public SerializationInput(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        super(clientSocket, notifier);
    }


    @Override
    public void run() {

        while(true){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ClientMessage inputObject = (ClientMessage) objectInputStream.readObject();
                var res = handleMessage(inputObject);
                if(res != null){
                    notifier.notifyOutput(clientSocket, res);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public ServerMessage handleMessage(ClientMessage message) {
        switch (message.type()) {
            case LOGIN -> {
                clients.put(clientSocket, message.body());
                return new ServerMessage(
                        ServerMessage.Error.SUCCESS, ServerMessage.Type.LOGIN_RESPONSE,
                        Collections.singletonList(String.valueOf(sessionID.getAndIncrement())));
            }
            case MESSAGE -> {
                messages.add(new TextMessage(clients.get(clientSocket), message.body()));
                var data = messages.stream()
                        .map(msg -> msg.name() + ": " + msg.text())
                        .toList();
                for(var client: clients.keySet()){
                    notifier.notifyOutput(client, new ServerMessage(ServerMessage.Error.SUCCESS, ServerMessage.Type.MESSAGE_LIST_UPDATED, data));
                }
                return null;
            }
            case LIST -> {
                System.out.println("LIST " + clients);
                var data = new ArrayList<>(clients.values());
                return new ServerMessage(ServerMessage.Error.SUCCESS, ServerMessage.Type.MEMBERS_LIST_UPDATED, data);
            }
            case LOGOUT -> {
                System.out.println("LOGOUT");
                clients.remove(clientSocket);
                return null;
            }

            default -> throw new RuntimeException("Strange message");
        }
    }



}

