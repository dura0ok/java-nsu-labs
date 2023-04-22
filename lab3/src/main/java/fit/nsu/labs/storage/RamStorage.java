package fit.nsu.labs.storage;

import fit.nsu.labs.components.CarComponent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RamStorage implements IStorage<CarComponent>{
    private final BlockingQueue<CarComponent> components;
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
    public void put(CarComponent newItem) throws InterruptedException {
        components.put(newItem);
    }

    @Override
    public CarComponent get() throws InterruptedException {
        return components.take();
    }
}
