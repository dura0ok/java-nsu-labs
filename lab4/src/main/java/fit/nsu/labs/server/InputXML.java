package fit.nsu.labs.server;

import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;
import fit.nsu.labs.common.StaticOutput;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.Socket;

public class InputXML extends InputHandler {
    public InputXML(Socket clientSocket, StaticOutput<ServerMessage> notifier) {
        super(clientSocket, notifier);
    }

    @Override
    public void run() {
        try (var stream = new DataInputStream(clientSocket.getInputStream());) {
            while (true) {
                var length = stream.readInt();
                var xmlBytes = stream.readNBytes(length);
                var msg = ClientMessage.deserialize(xmlBytes);
                System.out.println(msg);
                handleMessage(msg);
            }
        } catch (EOFException e){
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

