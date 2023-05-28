package fit.nsu.labs.server;

import fit.nsu.labs.common.message.server.ServerMessage;
import fit.nsu.labs.server.exception.UserExistsException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserManager {
    // sessionId, User
    private final ConcurrentMap<Integer, User> users = new ConcurrentHashMap<>();

    public void createUser(String name, ClientConnection connection) throws UserExistsException {
        if (users.values().stream().anyMatch(u -> u.name().equalsIgnoreCase(name))) {
            throw new UserExistsException();
        }

        var user = new User(name, connection);
        users.put(connection.getSessionId(), user);
    }

    public List<User> listUsers() {
        return List.copyOf(users.values());
    }

    public Optional<User> getBySessionId(int sessionId) {
        return Optional.ofNullable(users.get(sessionId));
    }

    public void broadcastSend(ServerMessage serverMessage) {
        users.values().forEach(u -> u.connection().send(serverMessage));
    }

    public void logout(int sessionId) {
        if (!users.containsKey(sessionId)) return;
        users.remove(sessionId);
    }
}
