package fit.nsu.labs;

import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.executor.CarBuildExecutor;
import fit.nsu.labs.model.executor.ComponentExecutor;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.storage.RamStorage;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            var componentsSpeedRate = Integer.parseInt(dotenv.get("COMPONENTS_SPEED_RATE"));

            var bodyStorageCapacity = Integer.parseInt(dotenv.get("STORAGE_BODY_CAPACITY"));
            var engineStorageCapacity = Integer.parseInt(dotenv.get("STORAGE_ENGINE_CAPACITY"));
            var accesoryStorageCapacity = Integer.parseInt(dotenv.get("STORAGE_ACCESSORY_CAPACITY"));

            var bodyWorkersCount = Integer.parseInt(dotenv.get("WORKERS_BODY_COUNT"));
            var engineWorkersCount = Integer.parseInt(dotenv.get("WORKERS_ENGINE_COUNT"));
            var accessoryWorkersCount = Integer.parseInt(dotenv.get("WORKERS_ACCESSORY_COUNT"));

            var workersBuildCarCount = Integer.parseInt(dotenv.get("WORKERS_BUILD_CAR_COUNT"));
            var storageCarsCapacity = Integer.parseInt(dotenv.get("STORAGE_CARS_CAPACITY"));


            var bodyExecutor = new ComponentExecutor<>(CarBody.class, componentsSpeedRate, bodyStorageCapacity, bodyWorkersCount);
            var engineExecutor = new ComponentExecutor<>(CarEngine.class, componentsSpeedRate, engineStorageCapacity, engineWorkersCount);
            var accessoryExecutor = new ComponentExecutor<>(CarAccessory.class, componentsSpeedRate, accesoryStorageCapacity, accessoryWorkersCount);

            CarBuildExecutor carBuildExecutor = new CarBuildExecutor.Builder()
                    .withWorkersCount(4)
                    .withCarBodyFactory(new CarComponentFactory<>(CarBody.class, bodyExecutor.getFactory().getStorage()))
                    .withCarEngineFactory(new CarComponentFactory<>(CarEngine.class, engineExecutor.getFactory().getStorage()))
                    .withCarAccessoryFactory(new CarComponentFactory<>(CarAccessory.class, accessoryExecutor.getFactory().getStorage()))
                    .withCarStorage(new RamStorage<>(10))
                    .withRate(1)
                    .build();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}