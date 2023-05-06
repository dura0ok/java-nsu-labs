package fit.nsu.labs.client.protocol;

import fit.nsu.labs.server.Message;

import java.io.IOException;

public interface ChatClientProtocol {
    void Login(String userName) throws IOException;

    Message sendText();
}
