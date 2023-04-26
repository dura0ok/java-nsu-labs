package fit.nsu.labs.tasks;

import fit.nsu.labs.components.CarComponent;
import fit.nsu.labs.components.CarComponentFactory;

public class ProduceComponent<T extends CarComponent> implements Task {
    private final CarComponentFactory<T> factory;

    public ProduceComponent(CarComponentFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public String getTaskName() {
        return "produce component";
    }

    @Override
    public void run() {
        try {
            while (true) {
                factory.getStorage().put(factory.produceElement());
                Thread.sleep(6000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
