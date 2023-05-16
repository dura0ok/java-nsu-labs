package fit.nsu.labs.server;

import fit.nsu.labs.common.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Input extends InputHandler {
    private final static AtomicInteger sessionID = new AtomicInteger(1);
    private static final Map<Socket, User> clients = new HashMap<>();

    public Input(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        super(clientSocket, notifier);
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("handle message");
                handleMessage(ClientMessage.deserialize(clientSocket.getInputStream()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

    }

    public void handleMessage(ClientMessage message) {
        System.out.println(message);
        if (message.getClass().equals(ClientMessage.LoginRequest.class)) {
            var loginResponse = new ServerMessage.LoginResponse(sessionID.getAndIncrement());
            clients.put(clientSocket, new User(((ClientMessage.LoginRequest) message).getUserName(), loginResponse.getSessionID()));
            notifier.notifyOutput(clientSocket, loginResponse);

            var lastMessages = getLastNMessages(Integer.parseInt(System.getProperty("LAST_MESSAGES_SEND")));
            System.out.println("last messages =====  ");
            for (var lastElement : lastMessages) {
                notifier.notifyOutput(clientSocket, generateNewMessageResponse(lastElement));
            }
        } else if (message.getClass().equals(ClientMessage.Message.class)) {
            var newMessage = new TextMessage(clients.get(clientSocket).name(), ((ClientMessage.Message) message).getMessageText());
            messages.add(newMessage);
            for (var client : clients.keySet()) {
                notifier.notifyOutput(client, generateNewMessageResponse(newMessage));
            }
        } else if (message.getClass().equals(ClientMessage.ListMembers.class)) {
            System.out.println("LIST " + clients);
            var data = new ArrayList<>(clients.values());
            var listResponse = new ServerMessage.ListMembers(data);
            notifier.notifyOutput(clientSocket, listResponse);
        } else if (message.getClass().equals(ClientMessage.Logout.class)) {
            System.out.println("LOGOUT");
            clients.remove(clientSocket);
        } else {
            throw new RuntimeException("Strange message");
        }
    }


}

