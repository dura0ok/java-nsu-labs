package fit.nsu.labs.client.model.handlers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import fit.nsu.labs.client.model.ChatClientModel;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;
import fit.nsu.labs.common.User;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;

public class Input implements Runnable {
    private final StaticOutput<ClientMessage> notifier;
    private final Socket clientSocket;
    private final ChatClientModel model;
    private final XStream xStream = new XStream(new StaxDriver());

    public Input(Socket clientSocket, ChatClientModel model, StaticOutput<ClientMessage> notifier) throws IOException {
        this.clientSocket = clientSocket;
        this.model = model;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        try {
            while (true) {

                var inputObject = ServerMessage.deserialize(clientSocket.getInputStream());
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
                    System.out.println(messageOutput);
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
