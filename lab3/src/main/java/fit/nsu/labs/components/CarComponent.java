package fit.nsu.labs.components;

import fit.nsu.labs.AtomicId;

public abstract class CarComponent extends AtomicId {
    CarComponent() {
        nextId();
    }

}