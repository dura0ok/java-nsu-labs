package fit.nsu.labs.components;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class CarComponentFactory<T extends CarComponent>{
    private final Class<T> componentClass;

    private T createComponent(){
        try {
            return (T) componentClass.getConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public CarComponentFactory(Class<T> componentClass){
        this.componentClass = componentClass;
    }

    public T produceElement(){
        return createComponent();
    }
}