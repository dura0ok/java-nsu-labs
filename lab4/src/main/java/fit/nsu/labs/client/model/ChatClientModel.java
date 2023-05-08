package fit.nsu.labs.client.model;

import fit.nsu.labs.Configuration;
import fit.nsu.labs.common.ClientMessage;
import fit.nsu.labs.common.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static fit.nsu.labs.Utils.serializeMessage;

public abstract class ChatClientModel implements Observable {
    private final List<OnEvent> onEvents = new ArrayList<>();
    private final String name;
    private final Socket clientSocket;
    private final Configuration configuration = new Configuration();
    private final OutputThread outputThread;


    public ChatClientModel(String name) {
        this.name = name;
        try {
            clientSocket = new Socket(configuration.getServerName(), configuration.getPort());
            outputThread = new OutputThread(clientSocket);
            InputThread inputThread = new InputThread(clientSocket, this);
            inputThread.start();
            outputThread.start();
            login();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    abstract void login();

    public OutputThread getOutputThread() {
        return outputThread;
    }

    public String getName() {
        return name;
    }

    @Override
    public void registerObserver(OnEvent o) {
        onEvents.add(o);
    }

    public void notifyObservers(Event event) {
        for (var onEvent : onEvents) {
            onEvent.notification(event);
        }
    }

    public static class ModelData {
        private final List<String> messages = new ArrayList<>();

        private final List<String> users = new ArrayList<>();

        public List<String> getMessages() {
            return messages;
        }

        public List<String> getUsers() {
            return users;
        }
    }
}

class InputThread extends Thread {
    private final Socket clientSocket;
    private final ChatClientModel model;

    InputThread(Socket clientSocket, ChatClientModel model) {
        this.clientSocket = clientSocket;
        this.model = model;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ServerMessage inputObject = (ServerMessage) objectInputStream.readObject();
                switch (inputObject.eventName()){
                    case LOGIN_RESPONSE ->  System.out.println("you logged in");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class OutputThread extends Thread {
    private final OutputStream out;
    private final Object lock;
    private ClientMessage dataToSend;

    OutputThread(Socket socket) throws IOException {
        out = socket.getOutputStream();
        lock = new Object();
    }

    public void run() {
        synchronized (lock) {
            while (true) {
                try {
                    lock.wait();
                    System.out.println("asd");
                    if (dataToSend != null) {
                        byte[] serializedObject = serializeMessage(dataToSend);
                        out.write(serializedObject);
                        dataToSend = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send(ClientMessage data) {
        synchronized (lock) {
            dataToSend = data;
            System.out.println("Data to send " + dataToSend);
            lock.notify();
        }
    }
}