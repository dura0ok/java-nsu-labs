package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;
import fit.nsu.labs.model.CarProduct;
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
    private final StatisticPanel bodyStat;
    private final StatisticPanel engineStat;
    private final StatisticPanel accessoryStat;
    private final StatisticPanel carStat;

    public GraphicsView(CarManufacturer carManuFacturer) throws ConfigException {
        carManuFacturer.registerObserver(this);
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        JPanel bodySpeedSlider = new SliderPanel("body rate", carManuFacturer, CarBody.class);
        JPanel engineSpeedSlider = new SliderPanel("engine rate", carManuFacturer, CarEngine.class);
        JPanel accesorySpeedSlider = new SliderPanel("accessory rate", carManuFacturer, CarAccessory.class);
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

        carStat = new StatisticPanel("car stat");
        add(carStat);
        carManuFacturer.start();
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
        //System.out.println(event);
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

        if (event.type() == CarProduct.class) {
            carStat.setStorageSize(event.storageSize());
            carStat.updateProducedNumber(event.totalProduced());
        }
    }
}
