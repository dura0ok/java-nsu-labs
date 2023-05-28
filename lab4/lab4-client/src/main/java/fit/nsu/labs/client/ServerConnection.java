package fit.nsu.labs.client;

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
import java.util.concurrent.BlockingQueue;

public class ServerConnection {
    private final Socket socket;

    private final BlockingQueue<IncomingQueueItem> incomingQueue;
    private final BlockingQueue<OutgoingQueueItem> outgoingQueue;
    private final Decoder<ServerMessage> decoder;
    private final Encoder<ClientMessage> encoder;

    private final Thread in;
    private final Thread out;

    public ServerConnection(Socket socket,
                            BlockingQueue<IncomingQueueItem> incomingQueue, BlockingQueue<OutgoingQueueItem> outgoingQueue,
                            Decoder<ServerMessage> decoder, Encoder<ClientMessage> encoder) {
        this.socket = socket;
        this.incomingQueue = incomingQueue;
        this.outgoingQueue = outgoingQueue;
        this.decoder = decoder;
        this.encoder = encoder;

        this.in = new Thread(this::incoming, "Input handler");
        this.out = new Thread(this::outgoing, "Output handler");

    }

    public void start() {
        System.out.println("[DEBUG] [connection] open");
        in.start();
        out.start();
    }
    
    private void incoming() {
        System.out.println("[DEBUG] [connection.incoming] begin loop");
        try {
            while (!Thread.interrupted()) {
                try {
                    incomingQueue.add(new IncomingQueueItem(
                            decoder.decode(socket.getInputStream()),
                            this));
                } catch (DecoderException e) {
                    if (e.getCause() instanceof EOFException) {
                        // Что-то пошло не так. Закрываем все соединения.
                        this.close();
                        break;
                    }

                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            //TODO Что-то со связью с сервером.
            throw new RuntimeException(e);
        } finally {
            System.out.println("[DEBUG] [connection.incoming] end loop");
        }
    }

    private void outgoing() {
        try {
            System.out.println("[DEBUG] [connection.outgoing] begin loop");
            while (!Thread.interrupted()) {
                try {
                    var queueItem = outgoingQueue.take();
                    var serverMessage = queueItem.message();
                    var outputStream = socket.getOutputStream();

                    byte[] bytes = encoder.encode(serverMessage);
                    //TODO в ENV сделать выключатель для дампов
                    DumpUtil.dumpPacket(bytes, "Dump outgoing packet");

                    outputStream.write(bytes);
                    outputStream.flush();
                    if (queueItem.afterTask() != null) {
                        new Thread(queueItem.afterTask()).start();
                    }
                } catch (EncoderException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException ignore) {
            // просто выходим
        } catch (IOException e) {
            //TODO Что-то со связью с сервером.
            throw new RuntimeException(e);
        } finally {
            System.out.println("[DEBUG] [connection.outgoing] end loop");
        }
    }

    public void send(ClientMessage message) {
        send(message, null);
    }

    public void send(ClientMessage message, Runnable afterTask) {
        outgoingQueue.add(new OutgoingQueueItem(message, afterTask));
    }

    public void close() {
        System.out.println("[DEBUG] [connection] close");
        if (!in.isInterrupted()) in.interrupt();
        if (!out.isInterrupted()) out.interrupt();
        try {
            if (!socket.isClosed()) socket.close();
        } catch (IOException ignore) {
            //игнорируем
        }
    }
}
