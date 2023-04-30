package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;
import fit.nsu.labs.model.exceptions.ManufactoryException;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GraphicsView extends JFrame {
    private final JPanel bodySpeedSlider;
    private final JPanel engineSpeedSlider;
    private final JPanel accesorySpeedSlider;
    private final Map<JSlider, String> sliders = new HashMap<>();
    private final CarManufacturer model;

    public GraphicsView(CarManufacturer carManuFacturer) {
        this.model = carManuFacturer;
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
        bodySpeedSlider = new SliderPanel(carManuFacturer, "Body", "BODY_SPEED_RATE");
        engineSpeedSlider = new SliderPanel(carManuFacturer, "Engine", "ENGINE_SPEED_RATE");
        accesorySpeedSlider = new SliderPanel(carManuFacturer, "Accessory", "ACCESSORY_SPEED_RATE");
        // Set up the JFrame
        setTitle("Factory");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(bodySpeedSlider);
        add(engineSpeedSlider);
        add(accesorySpeedSlider);
        setVisible(true);

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
}
