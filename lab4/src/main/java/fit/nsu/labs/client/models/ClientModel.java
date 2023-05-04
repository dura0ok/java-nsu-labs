package fit.nsu.labs.client.models;

import fit.nsu.labs.server.Message;

public interface ClientModel {
    void register(String name);
    Message send(String message);
}
