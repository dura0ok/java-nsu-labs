package fit.nsu.labs.client.model.handlers.serialization;

import fit.nsu.labs.client.model.ChatClientModel;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Input implements Runnable {
    private final StaticOutput<ClientMessage> notifier;
    private final Socket clientSocket;
    private final ChatClientModel model;

    public Input(Socket clientSocket, ChatClientModel model, StaticOutput<ClientMessage> notifier) {
        this.clientSocket = clientSocket;
        this.model = model;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ServerMessage inputObject = (ServerMessage) objectInputStream.readObject();
                System.out.println(inputObject);
                switch (inputObject.eventName()) {
                    case LOGIN_RESPONSE -> {
                        System.out.println("you logged in");
                        model.setSessionID(Integer.parseInt(inputObject.data().get(0)));
                    }
                    case MEMBERS_LIST_UPDATED -> {
                        System.out.println("response from server " + inputObject);
                        model.notifyObservers(new Event(Event.EventType.MEMBERS_UPDATED, inputObject.data()));
                    }
                    case MESSAGE_LIST_UPDATED -> {
                        model.notifyObservers(new Event(Event.EventType.MESSAGE_UPDATED, inputObject.data()));
                    }
                    default -> throw new RuntimeException("asd");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
