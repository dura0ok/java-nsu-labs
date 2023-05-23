package fit.nsu.labs.client.model;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.client.model.handlers.InputSerialization;
import fit.nsu.labs.client.model.handlers.InputXML;
import fit.nsu.labs.client.model.handlers.OutputSerialization;
import fit.nsu.labs.client.model.handlers.OutputXML;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        switch (System.getProperty("PROTOCOL")) {
            case "XML" -> {
                new Thread(new InputXML(clientSocket, this, notifier)).start();
                new Thread(new OutputXML(clientSocket, notifier)).start();
            }
            case "SERIALIZATION" -> {
                new Thread(new OutputSerialization(clientSocket, notifier)).start();
                new Thread(new InputSerialization(clientSocket, this, notifier)).start();
            }
            default -> throw new RuntimeException("invalid protocol");
        }


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

    void login() {
        try {
            notifier.notifyOutput(clientSocket, new ClientMessage.LoginRequest(getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMembersRequest() {
        try {
            notifier.notifyOutput(clientSocket, new ClientMessage.ListMembers(getSessionID()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        System.out.println("logout");
        try {
            notifier.notifyOutput(clientSocket, new ClientMessage.Logout(getSessionID()));


            while(!notifier.isEmpty(clientSocket)){
                System.out.println(notifier.isEmpty(clientSocket));
                Thread.sleep(100);
            }



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
            notifier.notifyOutput(clientSocket, new ClientMessage.Message(getSessionID(), text));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
