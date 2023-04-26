package fit.nsu.labs;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicId {
    protected static final AtomicLong id = new AtomicLong(0);

    private static final AtomicLong counter = new AtomicLong(0);

    public static void nextId() {
        id.incrementAndGet();
    }

    public long getID() {
        return id.get();
    }
}
