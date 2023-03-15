package fit.nsu.labs.views;


import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphics extends MineSweeperViewer {

    private final FieldElement[][] buttons = new FieldElement[rowSize][columnSize];
    private final JFrame frame = new JFrame();

    public Graphics(GameField field, int columnSize, int rowSize) {
        super(field, columnSize, rowSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new FieldElementsGrid(columnSize, rowSize, buttons));
        frame.setVisible(true);
        addActionListener(buttons);
    }

    @Override
    public Dot clickButton() {
        return null;
    }

    @Override
    public void reDrawField() {

    }

    private void addActionListener(FieldElement[][] buttons) {
        var listener = new FieldElementListener();
        for (JButton[] jButtons : buttons) {
            for (JButton jButton : jButtons) {
                jButton.addActionListener(listener);
            }
        }
    }
}

class FieldElement extends JButton {
    private final Dot dot;

    public FieldElement(String text, Dot dot) {
        super(text);
        this.dot = dot;
    }

    public Dot getDot() {
        return dot;
    }

}

class FieldElementsGrid extends JPanel {
    public FieldElementsGrid(int columnSize, int rowSize, FieldElement[][] buttons) {
        setLayout(new GridLayout(columnSize, rowSize, 3, 3));
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                 String text = String.format("  [  %d,  %d  ]  ", col, row);
                FieldElement button = new FieldElement(text, new Dot(col, row));
                add(button);
                buttons[row][col] = button;
            }
        }

    }
}


class FieldElementListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        FieldElement sourceBtn = (FieldElement) e.getSource();
        var x = Integer.parseInt(String.valueOf(sourceBtn.getDot().getX()));
        var y = Integer.parseInt(String.valueOf(sourceBtn.getDot().getY()));


        String message = "Button pressed: " + actionCommand + " " + x + " " + y;
        JOptionPane.showMessageDialog(sourceBtn, message, "Button Pressed", JOptionPane.PLAIN_MESSAGE);
        sourceBtn.setText("Pressed");
        sourceBtn.setEnabled(false);
        var dot = new Dot(x,y);
    }
}

