package fit.nsu.labs.storage;

import fit.nsu.labs.products.CarProduct;

public class RamCarProductStorage extends RamStorage<CarProduct> {
    public RamCarProductStorage(int capacity) {
        super(capacity);
    }
}
