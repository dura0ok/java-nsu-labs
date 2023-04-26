package fit.nsu.labs.components;

import fit.nsu.labs.storage.RamCarComponentStorage;

import java.lang.reflect.InvocationTargetException;

public class CarComponentFactory<T extends CarComponent> {
    private final Class<T> componentClass;
    private final RamCarComponentStorage storage;

    public CarComponentFactory(Class<T> componentClass, RamCarComponentStorage storage) {
        this.componentClass = componentClass;
        this.storage = storage;
    }

    public RamCarComponentStorage getStorage() {
        return storage;
    }

    private T createComponent() {
        try {
            return (T) componentClass.getConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public T produceElement() throws InterruptedException {
        var el = createComponent();
        storage.put(el);
        return el;
    }
}