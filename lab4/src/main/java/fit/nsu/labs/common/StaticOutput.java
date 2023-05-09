package fit.nsu.labs.common;

import fit.nsu.labs.common.ServerMessage;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class StaticOutput<T> {
    private static final Object lock = new Object();
    private final Map<Socket, T> outputMap = new HashMap<>();

    public void notifyOutput(Socket socket, T data) {
        synchronized (lock) {
            outputMap.put(socket, data);
            lock.notifyAll();
        }
    }

    public T getOutput(Socket socket) throws InterruptedException {
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