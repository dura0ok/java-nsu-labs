package fit.nsu.labs.server.protocol;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.TextMessage;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class SimpleInput extends InputHandler {
    private final static AtomicInteger sessionID = new AtomicInteger(1);
    private static final Map<Socket, String> clients = new HashMap<>();


    public SimpleInput(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        super(clientSocket, notifier);
    }


    @Override
    public void run() {

        while (true) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ClientMessage inputObject = (ClientMessage) objectInputStream.readObject();
                handleMessage(inputObject);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void handleMessage(ClientMessage message) {
        switch (message.type()) {
            case LOGIN -> {
                clients.put(clientSocket, message.body());

                var loginResponse = new ServerMessage(
                        ServerMessage.Error.SUCCESS, ServerMessage.Type.LOGIN_RESPONSE,
                        Collections.singletonList(String.valueOf(sessionID.getAndIncrement())));
                notifier.notifyOutput(clientSocket, loginResponse);

                var lastMessages = getLastNMessages(Integer.parseInt(System.getProperty("LAST_MESSAGES_SEND")));
                System.out.println("last messages =====");
                for (var lastElement : lastMessages) {
                    notifier.notifyOutput(clientSocket, generateNewMessageResponse(lastElement));
                }
            }
            case MESSAGE -> {
                var newMessage = new TextMessage(clients.get(clientSocket), message.body());
                messages.add(newMessage);
                for (var client : clients.keySet()) {
                    notifier.notifyOutput(client, generateNewMessageResponse(newMessage));
                }
            }
            case LIST -> {
                System.out.println("LIST " + clients);
                var data = new ArrayList<>(clients.values());
                var listResponse = new ServerMessage(ServerMessage.Error.SUCCESS, ServerMessage.Type.MEMBERS_LIST_UPDATED, data);
                notifier.notifyOutput(clientSocket, listResponse);
            }
            case LOGOUT -> {
                System.out.println("LOGOUT");
                clients.remove(clientSocket);
            }

            default -> throw new RuntimeException("Strange message");
        }
    }


}

