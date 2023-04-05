package fit.nsu.labs.model;

public interface Observable {
    void registerObserver(Observer o);

    // TODO: usually notifyObservers is not the part of a public interface, because it's not meant to be called by the users.
    void notifyObservers(Event event);
}