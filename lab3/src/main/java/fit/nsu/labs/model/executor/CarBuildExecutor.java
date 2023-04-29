package fit.nsu.labs.model.executor;

import fit.nsu.labs.model.CarProduct;
import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.storage.RamStorage;
import fit.nsu.labs.model.tasks.BuildCar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CarBuildExecutor {
    private final int workersCount;
    private final CarComponentFactory<CarBody> carBodyFactory;
    private final CarComponentFactory<CarEngine> carEngineFactory;
    private final CarComponentFactory<CarAccessory> carAccessoryFactory;
    private final RamStorage<CarProduct> carStorage;
    private final ThreadPoolExecutor executor;

    private CarBuildExecutor(Builder builder) {
        this.workersCount = builder.workersCount;
        this.carBodyFactory = builder.carBodyFactory;
        this.carEngineFactory = builder.carEngineFactory;
        this.carAccessoryFactory = builder.carAccessoryFactory;
        this.carStorage = builder.carStorage;

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(workersCount);
        this.executor = (ThreadPoolExecutor) executor;
        executor.scheduleAtFixedRate(() -> executor.submit(new BuildCar(carBodyFactory.getStorage(), carEngineFactory.getStorage(), carAccessoryFactory.getStorage(), this.carStorage)), 0, builder.rate, TimeUnit.SECONDS);
    }

    public static class Builder {
        private int workersCount;
        private CarComponentFactory<CarBody> carBodyFactory;
        private CarComponentFactory<CarEngine> carEngineFactory;
        private CarComponentFactory<CarAccessory> carAccessoryFactory;
        private RamStorage<CarProduct> carStorage;
        private int rate;

        public Builder withWorkersCount(int workersCount) {
            this.workersCount = workersCount;
            return this;
        }

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

        public Builder withRate(int rate) {
            this.rate = rate;
            return this;
        }

        public CarBuildExecutor build() {
            return new CarBuildExecutor(this);
        }
    }
}