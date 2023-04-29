package fit.nsu.labs.model;

import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;

import java.util.concurrent.atomic.AtomicLong;

public class CarProduct {
    private static final AtomicLong id = new AtomicLong(0);
    private final CarBody body;
    private final CarEngine engine;
    private final CarAccessory accessory;

    public CarProduct(CarBody body, CarEngine engine, CarAccessory accessory) {
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
        id.incrementAndGet();
    }

    @Override
    public String toString() {
        return "CarProduct{" +
                "id=" + id.get() +
                ", body=" + body +
                ", engine=" + engine +
                ", accessory=" + accessory +
                '}';
    }
}
