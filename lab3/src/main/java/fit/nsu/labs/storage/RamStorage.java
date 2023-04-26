package fit.nsu.labs.storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

abstract class RamStorage<T> implements IStorage<T> {
    private final BlockingQueue<T> components;
    private final int capacity;

    public RamStorage(int capacity) {
        this.capacity = capacity;
        this.components = new ArrayBlockingQueue<>(this.capacity);
    }

    @Override
    public int getStorageCapacity() {
        return this.capacity;
    }

    @Override
    public int getLength() {
        return components.size();
    }

    @Override
    public void put(T newItem) throws InterruptedException {
        components.put(newItem);
    }

    @Override
    public T get() throws InterruptedException {
        return components.take();
    }
}
