package fit.nsu.labs.model.component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CarBody extends CarComponent {
    private static final AtomicInteger id = new AtomicInteger(0);

    public CarBody() {
        id.incrementAndGet();
    }

    @Override
    public String toString() {
        return "Car Body: " + id.get();
    }
}

