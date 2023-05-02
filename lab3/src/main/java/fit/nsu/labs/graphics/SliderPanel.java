package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;
import fit.nsu.labs.model.component.CarComponent;
import fit.nsu.labs.model.config.ConfigKeysManager;
import fit.nsu.labs.model.exceptions.ConfigException;

import javax.swing.*;

public class SliderPanel extends JPanel {
    private final JSlider slider;
    private final JLabel label;
    public SliderPanel(String sliderName, CarManufacturer factory, Class<? extends CarComponent> clazz) throws ConfigException {
        super();
        var defaultRate = Integer.parseInt(System.getProperty(ConfigKeysManager.getComponentKeys(clazz).get("rate")));
        slider = new JSlider(JSlider.HORIZONTAL, 1, 30, defaultRate);
        slider.setName(sliderName);
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        label = new JLabel();
        setLabelText();

        this.setLayout(layout);
        slider.addChangeListener(new GraphicsController(factory, label, sliderName));

        add(slider);
        add(label);
    }

    private void setLabelText() {
        label.setText(slider.getName() + ": " + slider.getValue());
    }
}
