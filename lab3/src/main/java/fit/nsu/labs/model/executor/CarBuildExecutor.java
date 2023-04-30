package fit.nsu.labs.model.executor;

import fit.nsu.labs.model.CarProduct;
import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.storage.RamStorage;
import fit.nsu.labs.model.tasks.BuildCar;

import java.util.concurrent.*;

public class CarBuildExecutor implements Runnable {
    private final int workersCount;
    private final CarComponentFactory<CarBody> carBodyFactory;
    private final CarComponentFactory<CarEngine> carEngineFactory;
    private final CarComponentFactory<CarAccessory> carAccessoryFactory;

    private final RamStorage<CarProduct> carStorage;
    private final ThreadPoolExecutor executor;
    private final int rate;

    private CarBuildExecutor(Builder builder) {
        this.workersCount = builder.workersCount;
        this.carBodyFactory = builder.carBodyFactory;
        this.carEngineFactory = builder.carEngineFactory;
        this.carAccessoryFactory = builder.carAccessoryFactory;
        this.carStorage = builder.carStorage;
        this.rate = builder.rate;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(workersCount);
        this.executor = (ThreadPoolExecutor) executor;
    }

    public void run() {
        var scheduler = (ScheduledExecutorService) executor;
        var remain = carStorage.getRemainingCapacity();
        for (int i = 0; i < remain; i++) {
            executor.execute(new BuildCar(carBodyFactory.getStorage(), carEngineFactory.getStorage(), carAccessoryFactory.getStorage(), this.carStorage));

        }
        //scheduler.scheduleAtFixedRate(() -> executor.submit(new BuildCar(carBodyFactory.getStorage(), carEngineFactory.getStorage(), carAccessoryFactory.getStorage(), this.carStorage)), 0, rate, TimeUnit.SECONDS);
    }

    public RamStorage<CarProduct> getCarStorage() {
        return carStorage;
    }

    public void start(ExecutorService carStorageControllerThread) {
        var scheduler = (ScheduledExecutorService) carStorageControllerThread;
        scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    public static class Builder {
        private int workersCount;
        private CarComponentFactory<CarBody> carBodyFactory;
        private CarComponentFactory<CarEngine> carEngineFactory;
        private CarComponentFactory<CarAccessory> carAccessoryFactory;
        private RamStorage<CarProduct> carStorage;
        private int rate;

        public Builder withCarBodyFactory(CarComponentFactory<CarBody> carBodyFactory) {
            this.carBodyFactory = carBodyFactory;
            return this;
        }

        public Builder withCarEngineFactory(CarComponentFactory<CarEngine> carEngineFactory) {
            this.carEngineFactory = carEngineFactory;
            return this;
        }

        public Builder withCarAccessoryFactory(CarComponentFactory<CarAccessory> carAccessoryFactory) {
            this.carAccessoryFactory = carAccessoryFactory;
            return this;
        }

        public Builder withCarStorage(RamStorage<CarProduct> carStorage) {
            this.carStorage = carStorage;
            return this;
        }


        public CarBuildExecutor build() {
            return new CarBuildExecutor(this);
        }


    }

}