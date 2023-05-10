package fit.nsu.labs.client.model;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.client.model.handlers.serialization.Input;
import fit.nsu.labs.client.model.handlers.serialization.Output;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public abstract class ChatClientModel implements Observable {
    protected final Output outputHandler;
    private final String name;
    private final Configuration configuration = new Configuration();
    private final Socket clientSocket;
    private final Input inputHandler;
    private final ArrayList<OnEvent> onEvents = new ArrayList<>();
    private Integer sessionID;

    ChatClientModel(String name) throws IOException {
        this.name = name;
        clientSocket = new Socket(configuration.getServerName(), configuration.getPort());
        outputHandler = new Output(clientSocket);
        inputHandler = new Input(clientSocket, this, outputHandler.getNotifier());
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

    abstract void login();

    public abstract void updateMembersRequest();

    public abstract void logout();

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(int id) {
        sessionID = id;
    }

    public abstract void sendTextMessage(String text);
}
