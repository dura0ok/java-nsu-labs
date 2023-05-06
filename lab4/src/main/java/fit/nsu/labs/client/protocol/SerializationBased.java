package fit.nsu.labs.client.protocol;

import fit.nsu.labs.client.Connection;
import fit.nsu.labs.server.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationBased implements ChatClientProtocol {
    private final Connection connection;

    public SerializationBased(Connection connection) {
        this.connection = connection;
    }

    private static byte[] convertObjectToBytes(Message msg) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(byteStream)) {
            ois.writeObject(msg);
            return byteStream.toByteArray();
        }
    }

    @Override
    public void Login(String userName) throws IOException {
        var message = new Message(Message.MessageType.LOGIN, "", userName);
        var bytes = convertObjectToBytes(message);
        connection.sendMessage(bytes);
    }

    @Override
    public Message sendText() {
        return null;
    }
}
