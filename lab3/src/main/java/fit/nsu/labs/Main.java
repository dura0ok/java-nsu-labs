package fit.nsu.labs;

import fit.nsu.labs.components.CarAccessory;
import fit.nsu.labs.components.CarBody;
import fit.nsu.labs.components.CarComponentFactory;
import fit.nsu.labs.components.CarEngine;
import fit.nsu.labs.storage.RamCarComponentStorage;
import fit.nsu.labs.storage.RamCarProductStorage;
import fit.nsu.labs.tasks.BuildCar;
import fit.nsu.labs.tasks.ProduceComponent;
import fit.nsu.labs.tasks.SellCar;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            var carBodyFactory = new CarComponentFactory<>(
                    CarBody.class,
                    new RamCarComponentStorage(Integer.parseInt(dotenv.get("STORAGE_BODY_SIZE")))
            );

            var carEngineFactory = new CarComponentFactory<>(
                    CarEngine.class,
                    new RamCarComponentStorage(Integer.parseInt(dotenv.get("STORAGE_ENGINE_SIZE")))
            );

            var carAccessoriesFactory = new CarComponentFactory<>(
                    CarAccessory.class,
                    new RamCarComponentStorage(Integer.parseInt(dotenv.get("STORAGE_ACCESORY_SIZE")))
            );

            var workers_count = Integer.parseInt(dotenv.get("WORKERS"));
            var workers = Executors.newFixedThreadPool(workers_count);

            var bodyWorker = Executors.newSingleThreadExecutor();
            bodyWorker.execute(new ProduceComponent<>(carBodyFactory));

            var engineWorker = Executors.newSingleThreadExecutor();
            engineWorker.execute(new ProduceComponent<>(carEngineFactory));

            var accessoriesCount = Integer.parseInt(dotenv.get("ACCESORS_SUPPLIERS"));
            var accessoriesWorkers = Executors.newFixedThreadPool(accessoriesCount);
            for (int i = 0; i < accessoriesCount; i++) {
                accessoriesWorkers.execute(new ProduceComponent<>(carAccessoriesFactory));
            }

            var carStorage = new RamCarProductStorage(Integer.parseInt(dotenv.get("STORAGE_AUTO_SIZE")));

            for (int i = 0; i < workers_count; i++) {
                workers.execute(new BuildCar(carBodyFactory, carEngineFactory, carAccessoriesFactory, carStorage));
            }

            var dealersCount = Integer.parseInt(dotenv.get("DEALERS"));
            var dealers = Executors.newFixedThreadPool(dealersCount);
            for (int i = 0; i < dealersCount; i++) {
                dealers.execute(new SellCar(carStorage));
            }

            workers.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}