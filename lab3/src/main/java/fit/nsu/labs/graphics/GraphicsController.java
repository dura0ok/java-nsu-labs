package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphicsController implements ChangeListener {
    private final String name;
    private final JLabel label;
    private final CarManufacturer factory;

    public GraphicsController(CarManufacturer factory, JLabel label, String name) {
        this.name = name;
        this.label = label;
        this.factory = factory;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        setLabelText(slider);
        var componentName = name.split(" ")[0];
        var newValue = slider.getValue();
        if (componentName.equalsIgnoreCase("body")) {
            factory.getBodyExecutor().reschedule(newValue);
        }

        if (componentName.equalsIgnoreCase("engine")) {
            factory.getEngineExecutor().reschedule(newValue);
        }

        if (componentName.equalsIgnoreCase("accessory")) {
            factory.getAccessoryExecutor().reschedule(newValue);
        }
    }

    private void setLabelText(JSlider slider) {
        label.setText(name + ": " + slider.getValue());
    }
}
