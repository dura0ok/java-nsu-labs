package fit.nsu.labs.server.handler;

import fit.nsu.labs.common.message.client.Logout;
import fit.nsu.labs.common.message.server.EmptySuccess;
import fit.nsu.labs.common.message.server.ErrorMessage;
import fit.nsu.labs.common.message.server.UserLogoutEvent;
import fit.nsu.labs.server.ClientConnection;
import fit.nsu.labs.server.UserManager;

public class LogoutHandler implements MessageHandler<Logout> {
    private final UserManager userManager;

    public LogoutHandler(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void handle(Logout message, ClientConnection connection) {
        System.out.println("[DEBUG] [handler.Logout]");

        if (message.getSessionID() != connection.getSessionId()) {
            var error = new ErrorMessage();
            error.setMessage("Указанная сессия отличается от сессии пользователя");
            connection.send(error);
        } else {
            var user = userManager.getBySessionId(connection.getSessionId());

            userManager.logout(connection.getSessionId());
            connection.send(new EmptySuccess());
            connection.close();

            user.ifPresent(u -> {
                        var userLogoutEvent = new UserLogoutEvent();
                        userLogoutEvent.setUserName(u.name());
                        userManager.broadcastSend(userLogoutEvent);
                    });
        }
    }
}
