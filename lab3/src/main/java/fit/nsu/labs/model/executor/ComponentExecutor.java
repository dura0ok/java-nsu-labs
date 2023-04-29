package fit.nsu.labs.model.executor;

import fit.nsu.labs.model.component.CarComponent;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.storage.RamStorage;
import fit.nsu.labs.model.tasks.ConsumeComponent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ComponentExecutor<T extends CarComponent> {
    private final int rate;
    private final CarComponentFactory<T> factory;
    private final ThreadPoolExecutor executor;
    private int workersCount;

    public ComponentExecutor(Class<T> componentClass, int rate, int capacity, int workersCount) {
        this.rate = rate;
        this.workersCount = workersCount;
        factory = new CarComponentFactory<>(componentClass, new RamStorage<>(capacity));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(workersCount);
        this.executor = (ThreadPoolExecutor) executor;
        executor.scheduleAtFixedRate(() -> executor.submit(new ConsumeComponent<>(factory)), 0, rate, TimeUnit.SECONDS);
    }


    public CarComponentFactory<T> getFactory() {
        return factory;
    }


    public int getWorkersCount() {
        return workersCount;
    }

    public void setWorkersCount(int newWorkersCount) {
        this.workersCount = newWorkersCount;
        executor.setCorePoolSize(this.workersCount);
        executor.setMaximumPoolSize(this.workersCount);
    }


}
