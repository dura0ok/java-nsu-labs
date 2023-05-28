package fit.nsu.labs.common.message.server;

public class EmptySuccess extends ServerMessage {

    public EmptySuccess() {
        super(ErrorType.SUCCESS);
    }
}
