package fit.nsu.labs.graphics;


import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphicsController extends MouseAdapter {
    private final GameField model;


    public GraphicsController(GameField model) {
        this.model = model;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuFrame::new);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        SwingUtilities.invokeLater(() -> {
            FieldElement sourceBtn = (FieldElement) e.getSource();
            var x = Integer.parseInt(String.valueOf(sourceBtn.getDot().x()));
            var y = Integer.parseInt(String.valueOf(sourceBtn.getDot().y()));
            //System.out.println("performed " + new Dot(x, y));
            var clickedDot = new Dot(x, y);
            if (e.getButton() == MouseEvent.BUTTON1) {
                model.click(clickedDot);
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                model.updateFlag(clickedDot);
            }

//
//            String message = "Button pressed: " + actionCommand + " " + x + " " + y;
//            JOptionPane.showMessageDialog(sourceBtn, message, "Button Pressed", JOptionPane.PLAIN_MESSAGE);
//        sourceBtn.setText(String.valueOf(model.getElementByCoords(clickedDot).getBombsAroundCount()));
        });
    }
}

class FieldElement extends JButton {
    private final Dot dot;

    public FieldElement(String text, Dot dot) {
        super(text);
        this.dot = new Dot(dot.y(), dot.x());
//        int buttonWidth = getWidth();
//        int buttonHeight = getHeight();

//        Image scaledImage = icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_DEFAULT);
//        Icon scaledIcon = new ImageIcon(scaledImage);
//        setIcon(scaledIcon);

    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        Icon icon = getIcon();
        if (icon != null) {
            size.width = Math.max(size.width, icon.getIconWidth());
            size.height = Math.max(size.height, icon.getIconHeight());
        }
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dot getDot() {
        return dot;
    }

}