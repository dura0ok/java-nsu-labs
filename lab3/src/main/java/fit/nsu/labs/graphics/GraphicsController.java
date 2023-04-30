package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphicsController implements ChangeListener {
    private final String key;
    private final String name;
    private final JLabel label;
    private final CarManufacturer factory;

    public GraphicsController(CarManufacturer factory, JLabel label, String name, String key) {
        this.key = key;
        this.name = name;
        this.label = label;
        this.factory = factory;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        System.setProperty(key, String.valueOf(slider.getValue()));
        setLabelText(slider);
        if (name.equalsIgnoreCase("body")) {
            factory.getBodyExecutor().reschedule();
        }

        if (name.equalsIgnoreCase("engine")) {
            factory.getEngineExecutor().reschedule();
        }

        if (name.equalsIgnoreCase("accessory")) {
            factory.getAccessoryExecutor().reschedule();
        }
    }

    private void setLabelText(JSlider slider) {
        label.setText(name + ": " + slider.getValue());
    }
}
