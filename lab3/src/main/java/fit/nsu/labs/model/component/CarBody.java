package fit.nsu.labs.model.component;

import java.util.concurrent.atomic.AtomicLong;

public class CarBody extends CarComponent {
    private static final AtomicLong id = new AtomicLong(0);

    public CarBody() {
        id.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Car Body: " + id.get();
    }
}
