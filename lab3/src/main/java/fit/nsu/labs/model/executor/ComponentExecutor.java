package fit.nsu.labs.model.executor;

import fit.nsu.labs.model.CarManufacturer;
import fit.nsu.labs.model.component.CarComponent;
import fit.nsu.labs.model.config.ConfigKeysManager;
import fit.nsu.labs.model.exceptions.ManufactoryException;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.tasks.ConsumeComponent;

import java.util.Map;
import java.util.concurrent.*;

public class ComponentExecutor<T extends CarComponent> {
    private final CarComponentFactory<T> factory;
    private final ThreadPoolExecutor executor;
    private final Map<String, String> config;
    private final CarManufacturer model;
    private ScheduledFuture<?> future;

    private int rate;

    public ComponentExecutor(Class<T> componentClass, CarManufacturer model) throws ManufactoryException {
        config = ConfigKeysManager.getComponentKeys(componentClass);
        this.model = model;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(getWorkersCount());
        this.executor = (ThreadPoolExecutor) executor;
        factory = new CarComponentFactory<>(componentClass);
        rate = Integer.parseInt(System.getProperty(config.get("rate")));
    }

    public CarComponentFactory<T> getFactory() {
        return factory;
    }

    public int getWorkersCount() {
        return Integer.parseInt(System.getProperty(config.get("workersCount")));
    }

    public void start() {
        scheduleExecutor();
    }

    private void scheduleExecutor() {
        var scheduler = (ScheduledExecutorService) executor;
        future = scheduler.scheduleAtFixedRate(() -> executor.submit(new ConsumeComponent<>(factory, model)), 0, getRate(), TimeUnit.SECONDS);
    }

    public void reschedule(int newRate) {
        setRate(newRate);
        future.cancel(false);
        scheduleExecutor();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
