package fit.nsu.labs.common;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;


public class StaticOutput<T> {
    private final Object lock = new Object();
    private final ConcurrentHashMap<Socket, T> outputMap = new ConcurrentHashMap<>();

    public void notifyOutput(Socket socket, T data) {
        synchronized (lock) {
            while (outputMap.containsKey(socket)) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            outputMap.put(socket, data);
            lock.notifyAll();
        }
    }

    public T getOutput(Socket socket) throws InterruptedException {
        synchronized (lock) {
            while (!outputMap.containsKey(socket)) {
                lock.wait();
            }
            T output = outputMap.get(socket);
            outputMap.remove(socket);
            lock.notifyAll();
            return output;
        }
    }


    public boolean isEmpty(Socket socket){
        return !outputMap.containsKey(socket);
    }
}