package fit.nsu.labs.server;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.common.TextMessage;
import fit.nsu.labs.common.codec.*;
import fit.nsu.labs.common.message.client.*;
import fit.nsu.labs.common.message.server.ServerMessage;
import fit.nsu.labs.server.handler.IncomingMessageHandler;
import fit.nsu.labs.server.handler.ListMembersHandler;
import fit.nsu.labs.server.handler.LoginRequestHandler;
import fit.nsu.labs.server.handler.LogoutHandler;

import java.net.ServerSocket;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Lab4Server extends Thread {
    private final BlockingQueue<IncomingQueueItem> incomingQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger sessionGenerator = new AtomicInteger(1);

    private final int port;
    private final Decoder<ClientMessage> decoder;
    private final Encoder<ServerMessage> encoder;
    private final BlockingDeque<TextMessage> lastMessages;
    private final int countLastMessages;

    public Lab4Server(int port, Decoder<ClientMessage> decoder, Encoder<ServerMessage> encoder, int countLastMessages) {
        this.setName("Server");

        this.port = port;
        this.decoder = Objects.requireNonNull(decoder);
        this.encoder = Objects.requireNonNull(encoder);
        this.countLastMessages = countLastMessages;

        this.lastMessages = new LinkedBlockingDeque<>(countLastMessages);
    }

    @Override
    public void run() {
        var userManager = new UserManager();

        var router = new RouterMessages(incomingQueue,
                new LoginRequestHandler(userManager, lastMessages, countLastMessages),
                new ListMembersHandler(userManager),
                new IncomingMessageHandler(userManager, lastMessages, countLastMessages),
                new LogoutHandler(userManager));
        router.start();

        try (var serverSocket = new ServerSocket(port)){
            while (!Thread.interrupted()) {
                var socket = serverSocket.accept();
                var sessionId = sessionGenerator.getAndIncrement();

                new ClientConnection(sessionId, socket,
                        incomingQueue, decoder, encoder).start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Socket close.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var configuration = new Configuration();
        Encoder<ServerMessage> encoder = null;
        Decoder<ClientMessage> decoder = null;

        switch (configuration.getProtocol()) {
            case SERIALIZATION -> {
                encoder = new ObjectServerCodec();
                decoder = new ObjectClientCodec();
            }
            case XML -> {
                encoder = new XmlServerCodec();
                decoder = new XmlClientCodec();
            }
        }

        var server = new Lab4Server(configuration.getPort(), decoder, encoder, configuration.getCountMessages());
        server.start();

        System.out.printf("SERVER PORT: %d\n", configuration.getPort());
        System.out.printf("PROTOCOL: %s\n", configuration.getProtocol().name());
        System.out.printf("COUNT_MSG: %s\n", configuration.getCountMessages());

        server.join();
    }
}
