package fit.nsu.labs;

import fit.nsu.labs.components.CarBody;
import fit.nsu.labs.components.CarComponentFactory;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.out.println(dotenv.get("STORAGE_BODY_SIZE"));
        var carBodyFactory = new CarComponentFactory<>(CarBody.class);
        var item = carBodyFactory.produceElement();
        System.out.println(item.getID());
    }
}