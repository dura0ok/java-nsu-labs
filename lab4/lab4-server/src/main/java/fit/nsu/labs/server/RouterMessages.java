package fit.nsu.labs.server;

import fit.nsu.labs.common.message.client.ListMembers;
import fit.nsu.labs.common.message.client.LoginRequest;
import fit.nsu.labs.common.message.client.Logout;
import fit.nsu.labs.common.message.client.Message;
import fit.nsu.labs.server.handler.IncomingMessageHandler;
import fit.nsu.labs.server.handler.ListMembersHandler;
import fit.nsu.labs.server.handler.LoginRequestHandler;
import fit.nsu.labs.server.handler.LogoutHandler;

import java.util.concurrent.BlockingQueue;

public class RouterMessages extends Thread {

    private final BlockingQueue<IncomingQueueItem> incomingQueue;
    private final LoginRequestHandler loginRequestHandler;
    private final ListMembersHandler listMembersHandler;
    private final IncomingMessageHandler incomingMessageHandler;
    private final LogoutHandler logoutHandler;

    public RouterMessages(BlockingQueue<IncomingQueueItem> incomingQueue,
                          LoginRequestHandler loginRequestHandler, ListMembersHandler listMembersHandler,
                          IncomingMessageHandler incomingMessageHandler, LogoutHandler logoutHandler) {
        this.incomingQueue = incomingQueue;
        this.loginRequestHandler = loginRequestHandler;
        this.listMembersHandler = listMembersHandler;
        this.incomingMessageHandler = incomingMessageHandler;
        this.logoutHandler = logoutHandler;
        this.setName("Router incoming messages");
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                var queueItem = incomingQueue.take();

                if (queueItem.message().getClass().equals(LoginRequest.class)) {
                    loginRequestHandler.handle((LoginRequest) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(ListMembers.class)) {
                    listMembersHandler.handle((ListMembers) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(Message.class)) {
                    incomingMessageHandler.handle((Message) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(Logout.class)) {
                    logoutHandler.handle((Logout) queueItem.message(), queueItem.connection());
                } else {
                    System.err.printf("UNSUPPORTED '%s'", queueItem.message().getClass());
                }
            }
        } catch (InterruptedException ignore) {
            // просто выходим
        }
    }
}
