package fit.nsu.labs.client;

import java.io.IOException;

public interface Connection {
    void sendMessage(byte[] data) throws IOException;
}
