package fit.nsu.labs.components;

import java.util.concurrent.atomic.AtomicLong;

public abstract class CarComponent {
    private static final AtomicLong id = new AtomicLong(0);

    CarComponent(){
        nextId();
    }

    public long getID(){
        return id.get();
    }

    public static void nextId() {
        id.incrementAndGet();
    }
}