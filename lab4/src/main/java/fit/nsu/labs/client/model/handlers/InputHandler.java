package fit.nsu.labs.client.model.handlers;

import fit.nsu.labs.client.model.ChatClientModel;
import fit.nsu.labs.client.model.Event;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.User;

import java.io.IOException;
import java.util.Collections;

public abstract class InputHandler {
    protected void handle(ServerMessage inputObject, ChatClientModel model) throws IOException, ClassNotFoundException {
        if (inputObject.getClass().equals(ServerMessage.LoginResponse.class)) {
            System.out.println("you logged in");
            model.setSessionID(((ServerMessage.LoginResponse) inputObject).getSessionID());
        } else if (inputObject.getClass().equals(ServerMessage.ListMembers.class)) {
            var userList = ((ServerMessage.ListMembers) inputObject).getUserList();
    
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
}

