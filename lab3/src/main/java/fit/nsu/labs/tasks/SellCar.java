package fit.nsu.labs.tasks;

import fit.nsu.labs.AtomicId;
import fit.nsu.labs.storage.RamCarProductStorage;

public class SellCar extends AtomicId implements Task {
    private final RamCarProductStorage carStorage;

    public SellCar(RamCarProductStorage carStorage) {
        this.carStorage = carStorage;
    }

    @Override
    public String getTaskName() {
        return "sell car";
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
