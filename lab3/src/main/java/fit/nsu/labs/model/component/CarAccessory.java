package fit.nsu.labs.model.component;

import java.util.concurrent.atomic.AtomicLong;

public class CarAccessory extends CarComponent {
    private static final AtomicLong id = new AtomicLong(0);

    public CarAccessory() {
        id.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Car Accessory: " + id.get();
    }
}
