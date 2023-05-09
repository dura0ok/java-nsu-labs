package fit.nsu.labs.client.model;

import fit.nsu.labs.client.model.handlers.serialization.Input;
import fit.nsu.labs.client.model.handlers.serialization.Output;
import fit.nsu.labs.common.ClientMessage;

import java.io.IOException;

public class SerializationModel extends ChatClientModel{

    public SerializationModel(String name) throws IOException {
        super(name);
        var output = new Output(getClientSocket());
        new Thread(output).start();
        new Thread(new Input(getClientSocket(), this, output.getNotifier())).start();
        login();
    }

    @Override
    void login() {
        try {
            outputHandler.sendMessage(new ClientMessage(ClientMessage.MessageType.LOGIN, null, getName()));
        }catch (Exception e){
          throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMembersRequest() {
        try {
            var msg = new ClientMessage(ClientMessage.MessageType.LIST, getSessionID(), null);
            System.out.println("try to send " + msg);
            outputHandler.sendMessage(msg);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logout() {
        try {
            var msg = new ClientMessage(ClientMessage.MessageType.LOGOUT, getSessionID(), null);
            System.out.println("try to send logout" + msg);
            outputHandler.sendMessage(msg);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendTextMessage(String text) {
        try {
            var msg = new ClientMessage(ClientMessage.MessageType.MESSAGE, getSessionID(), text);
            System.out.println("try to send new message to server!!! " + msg);
            outputHandler.sendMessage(msg);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
