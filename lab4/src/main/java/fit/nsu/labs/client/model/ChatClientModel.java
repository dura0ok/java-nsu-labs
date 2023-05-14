package fit.nsu.labs.client.model;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.client.model.handlers.Input;
import fit.nsu.labs.client.model.handlers.Output;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClientModel implements Observable {
    private final String name;
    private final Configuration configuration = new Configuration();
    private final Socket clientSocket;
    private final ArrayList<OnEvent> onEvents = new ArrayList<>();
    private final StaticOutput<ClientMessage> notifier;
    private Integer sessionID;

    public ChatClientModel(String name) throws IOException {
        this.name = name;
        clientSocket = new Socket(configuration.getServerName(), configuration.getPort());
        notifier = new StaticOutput<>();
        var output = new Output(clientSocket, notifier);
        new Thread(output).start();
        new Thread(new Input(clientSocket, this, notifier)).start();
        login();
    }

    @Override
    public void registerObserver(OnEvent o) {
        onEvents.add(o);
    }

    public String getName() {
        return name;
    }

    public void notifyObservers(Event event) {
        for (var onEvent : onEvents) {
            onEvent.notification(event);
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    void login() {
        try {
            System.out.println("login send message!");
            notifier.notifyOutput(clientSocket, new ClientMessage.LoginRequest(getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMembersRequest() {
        try {
            System.out.println("login send message");
            notifier.notifyOutput(clientSocket, new ClientMessage.ListMembers(getSessionID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        try {
            System.out.println("login send message");
            notifier.notifyOutput(clientSocket, new ClientMessage.Logout(getSessionID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(int id) {
        sessionID = id;
    }

    public void sendTextMessage(String text) {
        try {
            System.out.println("login send message");
            notifier.notifyOutput(clientSocket, new ClientMessage.Message(getSessionID(), text));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
