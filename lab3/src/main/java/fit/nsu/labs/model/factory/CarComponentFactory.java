package fit.nsu.labs.model.factory;

import fit.nsu.labs.model.component.CarComponent;
import fit.nsu.labs.model.storage.RamStorage;

import java.lang.reflect.InvocationTargetException;

public class CarComponentFactory<T extends CarComponent> {
    private final Class<T> componentClass;
    private final RamStorage<T> storage;

    public CarComponentFactory(Class<T> componentClass, RamStorage<T> storage) {
        this.componentClass = componentClass;
        this.storage = storage;
    }

    public RamStorage<T> getStorage() {
        return storage;
    }

    private T createComponent() {
        try {
            return (T) componentClass.getConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println(componentClass.getName());
            throw new RuntimeException(e);
        }
    }

    public T produceElement() throws InterruptedException {
        var el = createComponent();
        storage.put(el);
        return el;
    }
}