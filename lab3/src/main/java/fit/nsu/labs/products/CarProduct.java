package fit.nsu.labs.products;

import fit.nsu.labs.components.CarAccessory;
import fit.nsu.labs.components.CarBody;
import fit.nsu.labs.components.CarEngine;

public class CarProduct {
    private final CarBody body;
    private final CarEngine engine;
    private final CarAccessory accessory;

    public CarProduct(CarBody body, CarEngine engine, CarAccessory accessory) {
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    @Override
    public String toString() {
        return "CarProduct{" +
                "body=" + body +
                ", engine=" + engine +
                ", accessory=" + accessory +
                '}';
    }
}
