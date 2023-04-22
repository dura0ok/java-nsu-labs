package fit.nsu.labs.storage;

public interface IStorage<T> {
    int getStorageCapacity();
    int getLength();
    void put (T newItem) throws InterruptedException;
    public T get () throws InterruptedException;
}
