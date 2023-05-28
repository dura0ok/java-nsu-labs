package fit.nsu.labs.client;

import fit.nsu.labs.client.handler.*;
import fit.nsu.labs.common.message.server.*;

import java.util.concurrent.BlockingQueue;

public class RouterMessages extends Thread {

    private final BlockingQueue<IncomingQueueItem> incomingQueue;
    private final IncomingMessageHandler incomingMessageHandler;
    private final LoginResponseHandler loginResponseHandler;
    private final SrvListMembersHandler listMembersHandler;
    private final UserLoginEventHandler userLoginEventHandler;
    private final UserLogoutEventHandler userLogoutEventHandler;

    public RouterMessages(BlockingQueue<IncomingQueueItem> incomingQueue,
                          IncomingMessageHandler incomingMessageHandler, LoginResponseHandler loginResponseHandler,
                          SrvListMembersHandler listMembersHandler, UserLoginEventHandler userLoginEventHandler,
                          UserLogoutEventHandler userLogoutEventHandler) {
        this.incomingQueue = incomingQueue;
        this.incomingMessageHandler = incomingMessageHandler;
        this.loginResponseHandler = loginResponseHandler;
        this.listMembersHandler = listMembersHandler;
        this.userLoginEventHandler = userLoginEventHandler;
        this.userLogoutEventHandler = userLogoutEventHandler;
        this.setName("Router incoming messages");
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                var queueItem = incomingQueue.take();

                if (queueItem.message().getClass().equals(NewMessage.class)) {
                    incomingMessageHandler.handle((NewMessage) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(LoginResponse.class)) {
                    loginResponseHandler.handle((LoginResponse) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(SrvListMembers.class)) {
                    listMembersHandler.handle((SrvListMembers) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(UserLoginEvent.class)) {
                    userLoginEventHandler.handle((UserLoginEvent) queueItem.message(), queueItem.connection());
                } else if (queueItem.message().getClass().equals(UserLogoutEvent.class)) {
                    userLogoutEventHandler.handle((UserLogoutEvent) queueItem.message(), queueItem.connection());
                } else {
                    System.err.printf("UNSUPPORTED '%s'", queueItem.message().getClass());
                }
            }
        } catch (InterruptedException ignore) {
            // просто выходим
        }
    }
}
