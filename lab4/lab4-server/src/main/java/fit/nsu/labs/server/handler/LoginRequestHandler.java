package fit.nsu.labs.server.handler;

import fit.nsu.labs.common.TextMessage;
import fit.nsu.labs.common.message.client.LoginRequest;
import fit.nsu.labs.common.message.server.*;
import fit.nsu.labs.server.ClientConnection;
import fit.nsu.labs.server.UserManager;
import fit.nsu.labs.server.exception.UserExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class LoginRequestHandler implements MessageHandler<LoginRequest> {
    private final UserManager userManager;
    private final BlockingDeque<TextMessage> lastMessages;
    private final int countLastMessages;

    public LoginRequestHandler(UserManager userManager, BlockingDeque<TextMessage> lastMessages, int countLastMessages) {
        this.userManager = userManager;
        this.lastMessages = lastMessages;
        this.countLastMessages = countLastMessages;
    }

    @Override
    public void handle(LoginRequest message, ClientConnection connection) {
        System.out.println("[DEBUG] [handler.LoginRequest]");

        try {
            userManager.createUser(message.getUserName(), connection);

            var loginResponse = new LoginResponse();
            loginResponse.setSessionID(connection.getSessionId());
            connection.send(loginResponse);

            var userLoginEvent = new UserLoginEvent();
            userLoginEvent.setUserName(message.getUserName());
            userManager.broadcastSend(userLoginEvent);

            if (!lastMessages.isEmpty()) {
                List<ServerMessage> messageList = new ArrayList<>(countLastMessages);
                for (TextMessage lastMessage : lastMessages) {
                    var msg = new NewMessage();
                    msg.setMessage(lastMessage);
                    messageList.add(msg);
                }
                connection.send(messageList);
            }
        } catch (UserExistsException e) {
            var error = new ErrorMessage();
            error.setMessage("Пользователь с именем '%s' уже существует".formatted(message.getUserName()));
            connection.send(error);
        }
    }
}
