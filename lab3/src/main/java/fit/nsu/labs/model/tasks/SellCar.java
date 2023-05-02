package fit.nsu.labs.model.tasks;

import fit.nsu.labs.model.CarProduct;
import fit.nsu.labs.model.storage.RamStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SellCar implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RamStorage<CarProduct> carStorage;

    public SellCar(RamStorage<CarProduct> carStorage) {
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {

            var car = carStorage.get();
            LOGGER.info("Dealer {} Auto {}", Thread.currentThread().getName(), car);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
