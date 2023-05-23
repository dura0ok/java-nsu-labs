package fit.nsu.labs.server;

import fit.nsu.labs.common.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

abstract public class InputHandler implements Runnable {
    protected static final List<TextMessage> messages = new ArrayList<>();
    private final static AtomicInteger sessionID = new AtomicInteger(1);
    private static final Map<Socket, User> clients = new HashMap<>();
    protected final Socket clientSocket;
    protected final StaticOutput<ServerMessage> notifier;

    public InputHandler(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        this.clientSocket = clientSocket;
        this.notifier = notifier;
    }

    protected List<TextMessage> getLastNMessages(int n) {
        List<Integer> res;
        if (n > messages.size()) {
            return messages;
        }
        return messages.subList(messages.size() - n, messages.size());
    }

    protected ServerMessage generateNewMessageResponse(TextMessage message) {
        return new ServerMessage.NewMessage(message);
    }

    protected void handleMessage(ClientMessage message) {
        if (message.getClass().equals(ClientMessage.LoginRequest.class)) {
            var loginResponse = new ServerMessage.LoginResponse(sessionID.getAndIncrement());
            clients.put(clientSocket, new User(((ClientMessage.LoginRequest) message).getUserName(), loginResponse.getSessionID()));
            notifier.notifyOutput(clientSocket, loginResponse);

            var lastMessages = getLastNMessages(Integer.parseInt(System.getProperty("LAST_MESSAGES_SEND")));
        
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
