package fit.nsu.labs.model.tasks;

import fit.nsu.labs.model.CarProduct;
import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.storage.RamStorage;

public class BuildCar implements Runnable {
    private final RamStorage<CarBody> carBodyStorage;
    private final RamStorage<CarEngine> carEngineStorage;
    private final RamStorage<CarAccessory> carAccessoryStorage;
    private final RamStorage<CarProduct> carStorage;

    public BuildCar(RamStorage<CarBody> carBodyStorage, RamStorage<CarEngine> carEngineStorage,
                    RamStorage<CarAccessory> carAccessoryStorage, RamStorage<CarProduct> carStorage) {
        this.carBodyStorage = carBodyStorage;
        this.carEngineStorage = carEngineStorage;
        this.carAccessoryStorage = carAccessoryStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {

            var body = carBodyStorage.get();
            var engine = carEngineStorage.get();
            var accessory = carAccessoryStorage.get();
            var product = new CarProduct((CarBody) body, (CarEngine) engine, (CarAccessory) accessory);
            Thread.sleep(5000);

            carStorage.put(product);
            System.out.println("Build " + product);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
