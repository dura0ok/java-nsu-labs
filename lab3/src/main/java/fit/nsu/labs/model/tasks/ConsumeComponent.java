package fit.nsu.labs.model.tasks;

import fit.nsu.labs.model.component.CarComponent;
import fit.nsu.labs.model.factory.CarComponentFactory;

public class ConsumeComponent<T extends CarComponent> implements Runnable {
    private final CarComponentFactory<T> factory;

    public ConsumeComponent(CarComponentFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            System.out.printf("%d %d\n", factory.getStorage().getStorageCapacity(), factory.getStorage().getLength());
            factory.produceElement();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
