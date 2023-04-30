package fit.nsu.labs.model.tasks;

import fit.nsu.labs.model.CarProduct;
import fit.nsu.labs.model.storage.RamStorage;

public class SellCar implements Runnable {
    private final RamStorage<CarProduct> carStorage;

    public SellCar(RamStorage<CarProduct> carStorage) {
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {

            var car = carStorage.get();
            System.out.println("Car sell " + car.toString());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
