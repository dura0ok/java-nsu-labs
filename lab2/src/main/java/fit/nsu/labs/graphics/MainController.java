package fit.nsu.labs.graphics;


import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private final GameField model;

    public MainController(GameField model) {
        this.model = model;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            String actionCommand = e.getActionCommand();
            FieldElement sourceBtn = (FieldElement) e.getSource();
            var x = Integer.parseInt(String.valueOf(sourceBtn.getDot().x()));
            var y = Integer.parseInt(String.valueOf(sourceBtn.getDot().y()));

            var clickedDot = new Dot(x, y);

            model.click(clickedDot);
//
            String message = "Button pressed: " + actionCommand + " " + x + " " + y;
            JOptionPane.showMessageDialog(sourceBtn, message, "Button Pressed", JOptionPane.PLAIN_MESSAGE);
//        sourceBtn.setText(String.valueOf(model.getElementByCoords(clickedDot).getBombsAroundCount()));
            sourceBtn.setEnabled(false);
        });
    }
}

class FieldElement extends JButton {
    private final Dot dot;

    public FieldElement(String text, Dot dot) {
        super(text);
        this.dot = dot;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var model = new GameField(10, 10, 5);
            var graphicsView = new GraphicsViewer(model);
            model.registerObserver(graphicsView);
            graphicsView.setVisible(true);


        });
    }

    public Dot getDot() {
        return dot;
    }

}