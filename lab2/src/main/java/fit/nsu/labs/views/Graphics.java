package fit.nsu.labs.views;


import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphics extends MineSweeperViewer implements ActionListener {

    private final FieldElement[][] buttons = new FieldElement[rowSize][columnSize];
    private final JFrame frame = new JFrame();

    Dot clickedDot = null;

    public Graphics(GameField field, int columnSize, int rowSize) {
        super(field, columnSize, rowSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new FieldElementsGrid(columnSize, rowSize, buttons));
        frame.setVisible(true);
        frame.setSize(500, 500);
        addActionListener(buttons);
    }

    @Override
    public synchronized Dot clickButton() {
        while(clickedDot == null){
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        var exited = clickedDot;
        clickedDot = null;
        return exited;
        
    }

    @Override
    public void reDrawField() {

    }

    private void addActionListener(FieldElement[][] buttons) {
        for (JButton[] jButtons : buttons) {
            for (JButton jButton : jButtons) {
                jButton.addActionListener(this);
            }
        }
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        FieldElement sourceBtn = (FieldElement) e.getSource();
        var x = Integer.parseInt(String.valueOf(sourceBtn.getDot().getX()));
        var y = Integer.parseInt(String.valueOf(sourceBtn.getDot().getY()));


        String message = "Button pressed: " + actionCommand + " " + x + " " + y;
        JOptionPane.showMessageDialog(sourceBtn, message, "Button Pressed", JOptionPane.PLAIN_MESSAGE);
        sourceBtn.setText("Pressed");
        sourceBtn.setEnabled(false);
        clickedDot = new Dot(x,y);
        this.notify();
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




