package fit.nsu.labs.client.model.handlers.serialization;

import fit.nsu.labs.client.model.ChatClientModel;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.User;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Collections;

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
                if (inputObject.getClass().equals(ServerMessage.LoginResponse.class)) {
                    System.out.println("you logged in");
                    model.setSessionID(((ServerMessage.LoginResponse) inputObject).getSessionID());
                } else if (inputObject.getClass().equals(ServerMessage.ListMembers.class)) {
                    System.out.println("response from server " + inputObject);
                    var userList = ((ServerMessage.ListMembers) inputObject).getUserList();
                    System.out.println(userList);
                    var nameList = userList.stream()
                            .map(User::name)
                            .toList();

                    model.notifyObservers(new Event(Event.EventType.MEMBERS_UPDATED, nameList));

                } else if (inputObject.getClass().equals(ServerMessage.NewMessage.class)) {
                    var messageOutput = ((ServerMessage.NewMessage) inputObject).getMessage();
                    model.notifyObservers(new Event(Event.EventType.MESSAGE_UPDATED, Collections.singletonList(messageOutput.toString())));
                } else {
                    throw new RuntimeException("asd");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
