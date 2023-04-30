package fit.nsu.labs.graphics;

import fit.nsu.labs.model.CarManufacturer;

import javax.swing.*;

public class SliderPanel extends JPanel {
    private final JSlider slider;
    private final String name;
    private final JLabel label;

    public SliderPanel(CarManufacturer factory, String name, String key) {
        super();
        this.name = name;
        slider = new JSlider(JSlider.HORIZONTAL, 1, 100, Integer.parseInt(System.getProperty(key)));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        label = new JLabel();
        setLabelText();

        this.setLayout(layout);
        slider.addChangeListener(new GraphicsController(factory, label, name, key));
        add(slider);
        add(label);
    }

    private void setLabelText() {
        label.setText(name + ": " + slider.getValue());
    }
}
