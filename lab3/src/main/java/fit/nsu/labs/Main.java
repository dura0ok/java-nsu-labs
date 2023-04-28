package fit.nsu.labs;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.factory.CarComponentFactory;
import fit.nsu.labs.model.storage.RamStorage;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            var bFactory = new CarComponentFactory<>(CarBody.class, new RamStorage<>(10));
            var body = bFactory.produceElement();
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}