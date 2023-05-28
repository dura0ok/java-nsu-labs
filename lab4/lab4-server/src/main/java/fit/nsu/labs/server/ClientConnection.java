package fit.nsu.labs.server;

import fit.nsu.labs.common.codec.Decoder;
import fit.nsu.labs.common.codec.DumpUtil;
import fit.nsu.labs.common.codec.Encoder;
import fit.nsu.labs.common.exception.DecoderException;
import fit.nsu.labs.common.exception.EncoderException;
import fit.nsu.labs.common.message.client.ClientMessage;
import fit.nsu.labs.common.message.server.ServerMessage;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnection {
    private final BlockingQueue<ServerMessage> outgoingQueue = new LinkedBlockingQueue<>();

    private final int sessionId;
    private final Socket socket;

    private final BlockingQueue<IncomingQueueItem> incomingQueue;
    private final Decoder<ClientMessage> decoder;
    private final Encoder<ServerMessage> encoder;

    private final Thread in;
    private final Thread out;

    public ClientConnection(int sessionId, Socket socket,
                            BlockingQueue<IncomingQueueItem> incomingQueue,
                            Decoder<ClientMessage> decoder, Encoder<ServerMessage> encoder) {
        this.sessionId = sessionId;
        this.socket = socket;
        this.incomingQueue = incomingQueue;
        this.decoder = decoder;
        this.encoder = encoder;

        this.in = new Thread(this::incoming, "(#%d) Input handler".formatted(sessionId));
        this.out = new Thread(this::outgoing, "(#%d) Output handler".formatted(sessionId));

    }

    public int getSessionId() {
        return sessionId;
    }

    public void start() {
        in.start();
        out.start();
    }

    public void send(ServerMessage message) {
        outgoingQueue.add(message);
    }

    public void send(ServerMessage... messages) {
        send(Arrays.asList(messages));
    }

    public void send(Collection<ServerMessage> messages) {
        outgoingQueue.addAll(messages);
    }

    public void close() {
        if (!in.isInterrupted()) in.interrupt();
        if (!out.isInterrupted()) out.interrupt();
        try {
            if (!socket.isClosed()) socket.close();
        } catch (IOException ignore) {
            //игнорируем
        }
    }

    private void incoming() {
        System.out.printf("[DEBUG] [connection.incoming (#%d)] begin loop\n", sessionId);
        try {
            while (!Thread.interrupted() && socket.isConnected()) {
                try {
                    incomingQueue.add(new IncomingQueueItem(
                            decoder.decode(socket.getInputStream()),
                            this));
                } catch (DecoderException e) {
                    if (e.getCause() instanceof EOFException) {
                        // Что-то пошло не так. Закрываем все соединения.
                        this.close();
                        break;
                    } else if (e.getCause() instanceof SocketException) {
                        if (e.getCause().getMessage().equals("Socket closed")
                                || !e.getCause().getMessage().equals("Socket is closed")) {
                            // Закрыли сокет. Ну и мы закроемся.
                            this.close();
                            break;
                        }
                    }

                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            if (!e.getMessage().equals("Socket closed") && !e.getMessage().equals("Socket is closed")) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            //TODO Что-то со связью с клиентом.
            //     Помимо вывода ошибки, нужно клиента удалить из списка "в сети".
            throw new RuntimeException(e);
        } finally {
            System.out.printf("[DEBUG] [connection.incoming (#%d)] end loop\n", sessionId);
        }
    }

    private void outgoing() {
        try {
            System.out.printf("[DEBUG] [connection.outgoing (#%d)] begin loop\n", sessionId);
            while (!Thread.interrupted() && socket.isConnected()) {
                try {
                    var serverMessage = outgoingQueue.take();
                    var outputStream = socket.getOutputStream();

                    byte[] bytes = encoder.encode(serverMessage);
                    //TODO в ENV сделать выключатель для дампов
                    DumpUtil.dumpPacket(bytes, "Dump outgoing packet");

                    outputStream.write(bytes);
                    outputStream.flush();
                } catch (EncoderException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException ignore) {
            // просто выходим
        } catch (SocketException e) {
            if (!e.getMessage().equals("Socket closed") && !e.getMessage().equals("Socket is closed")) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            //TODO Что-то со связью с клиентом.
            //     Помимо вывода ошибки, нужно клиента удалить из списка "в сети".
            throw new RuntimeException(e);
        } finally {
            System.out.printf("[DEBUG] [connection.outgoing (#%d)] end loop\n", sessionId);
        }
    }
}
