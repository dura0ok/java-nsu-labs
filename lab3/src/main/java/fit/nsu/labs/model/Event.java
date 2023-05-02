package fit.nsu.labs.model;

import fit.nsu.labs.model.component.CarComponent;

public record Event(Class<? extends CarComponent> type, int totalProduced, int storageSize) {
}
