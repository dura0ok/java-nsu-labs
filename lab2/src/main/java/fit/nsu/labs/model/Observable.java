package fit.nsu.labs.model;

public interface Observable {
    void registerObserver(Observer o);

    void notifyObservers(Event event);
}