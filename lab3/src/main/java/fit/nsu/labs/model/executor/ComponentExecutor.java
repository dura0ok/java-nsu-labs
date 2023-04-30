package fit.nsu.labs.model.executor;

import fit.nsu.labs.model.component.CarComponent;
import fit.nsu.labs.model.config.ConfigKeysManager;
import fit.nsu.labs.model.exceptions.ManufactoryException;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.storage.RamStorage;
import fit.nsu.labs.model.tasks.ConsumeComponent;

import java.util.Map;
import java.util.concurrent.*;

public class ComponentExecutor<T extends CarComponent> {
    private final CarComponentFactory<T> factory;
    private final ThreadPoolExecutor executor;
    private final Map<String, String> config;
    private ScheduledFuture<?> future;

    public ComponentExecutor(Class<T> componentClass) throws ManufactoryException {
        config = ConfigKeysManager.getComponentKeys(componentClass);
        factory = new CarComponentFactory<>(componentClass, new RamStorage<>(getCapacity()));
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(getWorkersCount());
        this.executor = (ThreadPoolExecutor) executor;
    }


    public CarComponentFactory<T> getFactory() {
        return factory;
    }


    public int getWorkersCount() {
        return Integer.parseInt(System.getProperty(config.get("workersCount")));
    }

    public int getRate() {
        System.out.println("RATE CONFIG " + config.get("rate"));
        return Integer.parseInt(System.getProperty(config.get("rate")));
    }

    public int getCapacity() {
        return Integer.parseInt(System.getProperty(config.get("capacity")));
    }

    public void setWorkersCount() {
        var newCount = getWorkersCount();
        executor.setCorePoolSize(newCount);
        executor.setMaximumPoolSize(newCount);
    }

    public void start() {
        scheduleExecutor();
    }

    private void scheduleExecutor() {
        var scheduler = (ScheduledExecutorService) executor;
        future = scheduler.scheduleAtFixedRate(() -> executor.submit(new ConsumeComponent<>(factory)), 0, getRate(), TimeUnit.SECONDS);
    }

    public void reschedule() {
        System.out.println("NEW RATE " + getRate());
        future.cancel(false);
        scheduleExecutor();
    }


}
