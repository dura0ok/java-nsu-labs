package fit.nsu.labs.client;

import fit.nsu.labs.client.handler.*;
import fit.nsu.labs.common.codec.Decoder;
import fit.nsu.labs.common.codec.Encoder;
import fit.nsu.labs.common.message.client.*;
import fit.nsu.labs.common.message.server.*;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lab4Client {
    private final BlockingQueue<IncomingQueueItem> incomingQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<OutgoingQueueItem> outgoingQueue = new LinkedBlockingQueue<>();

    private final String host;
    private final int port;
    private final Encoder<ClientMessage> encoder;
    private final Decoder<ServerMessage> decoder;

    private ServerConnection connection;
    private int sessionId;

    public Lab4Client(String host, int port, Encoder<ClientMessage> encoder, Decoder<ServerMessage> decoder) {
        this.host = host;
        this.port = port;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public void connect(MainWindow mainWindow) {
        try {
            var router = new RouterMessages(incomingQueue,
                    new IncomingMessageHandler(mainWindow),
                    new LoginResponseHandler(this),
                    new SrvListMembersHandler(mainWindow),
                    new UserLoginEventHandler(mainWindow),
                    new UserLogoutEventHandler(mainWindow));
            router.start();

            connection = new ServerConnection(new Socket(host, port), incomingQueue, outgoingQueue, decoder, encoder);
            connection.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void close() {
        connection.close();
    }

    public void login(String name) {
        var loginRequest = new LoginRequest();
        loginRequest.setUserName(name);
        connection.send(loginRequest);
    }

    public void requestListMembers() {
        var listMembers = new ListMembers();
        listMembers.setSessionID(sessionId);
        connection.send(listMembers);
    }

    public void sendTextMessage(String text) {
        var message = new Message();
        message.setSessionID(sessionId);
        message.setMessageText(text);
        connection.send(message);
    }

    public void logout(Runnable after) {
        var logout = new Logout();
        logout.setSessionID(sessionId);
        connection.send(logout, after);
    }
}
