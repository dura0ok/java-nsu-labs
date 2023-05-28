package fit.nsu.labs.client.handler;

import fit.nsu.labs.client.Lab4Client;
import fit.nsu.labs.client.ServerConnection;
import fit.nsu.labs.common.message.server.LoginResponse;

public class LoginResponseHandler implements MessageHandler<LoginResponse> {
    private final Lab4Client lab4Client;

    public LoginResponseHandler(Lab4Client lab4Client) {
        this.lab4Client = lab4Client;
    }

    @Override
    public void handle(LoginResponse message, ServerConnection connection) {
        System.out.println("[DEBUG] [handler.LoginResponse]");

        lab4Client.setSessionId(message.getSessionID());
    }
}
