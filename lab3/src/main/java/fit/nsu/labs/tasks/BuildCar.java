package fit.nsu.labs.tasks;

import fit.nsu.labs.AtomicId;
import fit.nsu.labs.components.CarAccessory;
import fit.nsu.labs.components.CarBody;
import fit.nsu.labs.components.CarComponentFactory;
import fit.nsu.labs.components.CarEngine;
import fit.nsu.labs.products.CarProduct;
import fit.nsu.labs.storage.RamCarProductStorage;

public class BuildCar extends AtomicId implements Task {
    private final CarComponentFactory<CarBody> carBodyFactory;
    private final CarComponentFactory<CarEngine> carEngineFactory;
    private final CarComponentFactory<CarAccessory> carAccessoryFactory;
    private final RamCarProductStorage carStorage;

    public BuildCar(CarComponentFactory<CarBody> carBodyFactory, CarComponentFactory<CarEngine> carEngineFactory,
                    CarComponentFactory<CarAccessory> carAccessoryFactory, RamCarProductStorage carStorage) {
        this.carBodyFactory = carBodyFactory;
        this.carEngineFactory = carEngineFactory;
        this.carAccessoryFactory = carAccessoryFactory;
        this.carStorage = carStorage;
        nextId();
    }


    @Override
    public String getTaskName() {
        return "build car";
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                var body = carBodyFactory.getStorage().get();
                var engine = carEngineFactory.getStorage().get();
                var accessory = carAccessoryFactory.getStorage().get();
                var product = new CarProduct((CarBody) body, (CarEngine) engine, (CarAccessory) accessory);
                carStorage.put(product);
                System.out.println("Build " + product);
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
