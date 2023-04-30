package fit.nsu.labs.model;

import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.exceptions.ManufactoryException;
import fit.nsu.labs.model.executor.CarBuildExecutor;
import fit.nsu.labs.model.executor.ComponentExecutor;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.tasks.SellCar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarManufacturer {
    private final CarBuildExecutor carBuildExecutor;
    private final ComponentExecutor<CarBody> bodyExecutor;
    private final ComponentExecutor<CarEngine> engineExecutor;
    private final ComponentExecutor<CarAccessory> accessoryExecutor;
    private final ExecutorService carStorageControllerThread;
    private final ExecutorService dealersExecutor;
    public CarManufacturer() throws ManufactoryException {

        bodyExecutor = new ComponentExecutor<>(CarBody.class);
        engineExecutor = new ComponentExecutor<>(CarEngine.class);
        accessoryExecutor = new ComponentExecutor<>(CarAccessory.class);

        carBuildExecutor = new CarBuildExecutor.Builder()
                .withCarBodyFactory(new CarComponentFactory<>(CarBody.class, bodyExecutor.getFactory().getStorage()))
                .withCarEngineFactory(new CarComponentFactory<>(CarEngine.class, engineExecutor.getFactory().getStorage()))
                .withCarAccessoryFactory(new CarComponentFactory<>(CarAccessory.class, accessoryExecutor.getFactory().getStorage()))
                .build();


        carStorageControllerThread = Executors.newSingleThreadScheduledExecutor();

        var dealersCount = getDealersCount();
        dealersExecutor = Executors.newScheduledThreadPool(dealersCount);
    }

    private static int getDealersCount() {
        return Integer.parseInt(System.getProperty("WORKERS_SELL_CAR_COUNT"));
    }

    private static int getSellCarSpeedRate() {
        return Integer.parseInt(System.getProperty("SELL_CAR_SPEED_RATE"));
    }

    public ComponentExecutor<CarBody> getBodyExecutor() {
        return bodyExecutor;
    }

    public ComponentExecutor<CarEngine> getEngineExecutor() {
        return engineExecutor;
    }

    public ComponentExecutor<CarAccessory> getAccessoryExecutor() {
        return accessoryExecutor;
    }

    public void start() {
        bodyExecutor.start();
        engineExecutor.start();
        accessoryExecutor.start();
        carBuildExecutor.start(carStorageControllerThread);
        var dealersScheduler = (ScheduledExecutorService) dealersExecutor;
        var dealersRate = getSellCarSpeedRate();
        dealersScheduler.scheduleAtFixedRate(new SellCar(carBuildExecutor.getCarStorage()), 0, dealersRate, TimeUnit.SECONDS);
    }
}
