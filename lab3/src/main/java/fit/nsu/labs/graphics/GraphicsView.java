package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;
import fit.nsu.labs.model.Event;
import fit.nsu.labs.model.OnEvent;
import fit.nsu.labs.model.component.CarAccessory;
import fit.nsu.labs.model.component.CarBody;
import fit.nsu.labs.model.component.CarEngine;
import fit.nsu.labs.model.config.ConfigKeysManager;
import fit.nsu.labs.model.exceptions.ConfigException;
import fit.nsu.labs.model.exceptions.ManufactoryException;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;

public class GraphicsView extends JFrame implements OnEvent {
    private final CarManufacturer model;
    private final StatisticPanel bodyStat;
    private final StatisticPanel engineStat;
    private final StatisticPanel accessoryStat;

    public GraphicsView(CarManufacturer carManuFacturer) throws ConfigException {
        this.model = carManuFacturer;
        model.registerObserver(this);
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        var bodyConfig = ConfigKeysManager.getComponentKeys(CarBody.class);
        var engineConfig = ConfigKeysManager.getComponentKeys(CarEngine.class);
        var accessoryConfig = ConfigKeysManager.getComponentKeys(CarBody.class);
        JPanel bodySpeedSlider = new SliderPanel(carManuFacturer, "Body", bodyConfig.get("rate"));
        JPanel engineSpeedSlider = new SliderPanel(carManuFacturer, "Engine", engineConfig.get("rate"));
        JPanel accesorySpeedSlider = new SliderPanel(carManuFacturer, "Accessory", accessoryConfig.get("rate"));
        // Set up the JFrame
        setTitle("Factory");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(bodySpeedSlider);
        add(engineSpeedSlider);
        add(accesorySpeedSlider);
        setVisible(true);


        bodyStat = new StatisticPanel("body stat");
        add(bodyStat);

        engineStat = new StatisticPanel("engine stat");
        add(engineStat);

        accessoryStat = new StatisticPanel("accessory stat");
        add(accessoryStat);
        model.start();
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        SwingUtilities.invokeLater(() -> {
            try {
                new GraphicsView(new CarManufacturer());
            } catch (ManufactoryException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void notification(Event event) {
        System.out.println(event);
        if (event.type() == CarBody.class) {
            bodyStat.setStorageSize(event.storageSize());
            bodyStat.updateProducedNumber(event.totalProduced());
        }

        if (event.type() == CarEngine.class) {
            engineStat.setStorageSize(event.storageSize());
            engineStat.updateProducedNumber(event.totalProduced());
        }

        if (event.type() == CarAccessory.class) {
            accessoryStat.setStorageSize(event.storageSize());
            accessoryStat.updateProducedNumber(event.totalProduced());
        }
    }
}
