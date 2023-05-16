package fit.nsu.labs.model.config;

import fit.nsu.labs.model.CarProduct;
import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.exceptions.ConfigException;

import java.util.HashMap;
import java.util.Map;

public class ConfigKeysManager {
    public static Map<String, String> getComponentKeys(Class<?> componentClass) throws ConfigException {
        Map<String, String> keys = new HashMap<>();
        if (componentClass == CarBody.class) {
            keys.put("rate", "BODY_SPEED_RATE");
            keys.put("capacity", "STORAGE_BODY_CAPACITY");
            keys.put("workersCount", "WORKERS_BODY_COUNT");
        }

        if (componentClass == CarEngine.class) {
            keys.put("rate", "ENGINE_SPEED_RATE");
            keys.put("capacity", "STORAGE_ENGINE_CAPACITY");
            keys.put("workersCount", "WORKERS_ENGINE_COUNT");
        }

        if (componentClass == CarAccessory.class) {
            keys.put("rate", "ACCESSORY_SPEED_RATE");
            keys.put("capacity", "STORAGE_ACCESSORY_CAPACITY");
            keys.put("workersCount", "WORKERS_ACCESSORY_COUNT");
        }

        if (componentClass == CarProduct.class) {
            keys.put("rate", "CAR_BUILD_SPEED_RATE");
            keys.put("capacity", "STORAGE_CARS_CAPACITY");
            keys.put("workersCount", "WORKERS_BUILD_CAR_COUNT");
        }

        if (keys.isEmpty()) {
            throw new ConfigException(componentClass);
        }
        return keys;
    }


}
