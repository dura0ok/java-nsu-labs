package fit.nsu.labs.client.model;

import fit.nsu.labs.common.ClientMessage;

public class SerializationModel extends ChatClientModel {

    public SerializationModel(String name) {
        super(name);
    }

    @Override
    void login() {
        var loginMessage = new ClientMessage(ClientMessage.MessageType.LOGIN, null, getName());
        getOutputThread().send(loginMessage);
    }
}
