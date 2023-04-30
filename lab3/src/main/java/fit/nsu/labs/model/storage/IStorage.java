package fit.nsu.labs.model.storage;

public interface IStorage<T> {
    int getStorageCapacity();

    int getRemainingCapacity();

    int getLength();

    void put(T newItem) throws InterruptedException;

    public T get() throws InterruptedException;
}
