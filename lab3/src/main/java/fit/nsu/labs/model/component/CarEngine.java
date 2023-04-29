package fit.nsu.labs.model.component;

import java.util.concurrent.atomic.AtomicLong;

public class CarEngine extends CarComponent {
    private static final AtomicLong id = new AtomicLong(0);

    public CarEngine() {
        id.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Car Engine: " + id.get();
    }
}
