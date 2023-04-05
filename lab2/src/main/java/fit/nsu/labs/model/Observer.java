package fit.nsu.labs.model;

public interface Observer {
    // TODO: the common naming convention for such methods in 'on<Something>' or 'handle<Something>'
    // e.g.: onEvent, onModelChange, handleEvent, handleModelChange, etc.
    void notification(Event event);
}

