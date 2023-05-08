package fit.nsu.labs.server;

import fit.nsu.labs.common.ServerMessage;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class StaticOutput {
    private static final Object lock = new Object();
    private static final Map<Socket, ServerMessage> outputMap = new HashMap<>();

    public static void notifyOutput(Socket socket, ServerMessage data) {
        synchronized (lock) {
            outputMap.put(socket, data);
            lock.notifyAll();
        }
    }

    public static ServerMessage getOutput(Socket socket) throws InterruptedException {
        synchronized (lock) {
            while (!outputMap.containsKey(socket)) {
                lock.wait();
            }
            var output = outputMap.get(socket);
            outputMap.remove(socket);
            return output;
        }
    }
}