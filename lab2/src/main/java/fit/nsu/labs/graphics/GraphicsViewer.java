package fit.nsu.labs.graphics;

import fit.nsu.labs.model.Event;
import fit.nsu.labs.model.*;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;


public class GraphicsViewer extends JFrame implements Observer {

    private final FieldElement[][] buttons;

    private final GameField model;
    private final MainController controller;

    public GraphicsViewer(GameField model) {
        this.model = model;
        controller = new MainController(this.model);
        buttons = new FieldElement[model.getColumnSize()][model.getRowSize()];
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new FieldElementsGrid(model.getColumnSize(), model.getRowSize(), buttons));
        addActionListener(buttons);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(500, 500);
        pack();
    }

    private void addActionListener(FieldElement[][] buttons) {
        for (JButton[] jButtons : buttons) {
            for (JButton jButton : jButtons) {
                jButton.addActionListener(controller);
            }
        }
    }

    @Override
    public void notification(Event event) {
        if (event.type().equals(EventType.REDRAW_REQUEST)) {
            reDraw(event);
            return;
        }
        if (event.type().equals(EventType.USER_WIN)) {
            showMessageDialog(null, "You win!");
            return;
        }

        if (event.type().equals(EventType.BOMB_OPENED)) {
            showMessageDialog(null, "Bomb opened");
            dispose();
            return;
        }
        if (event.type().equals(EventType.ALREADY_CLICKED)) {
            showMessageDialog(null, "Already clicked");
        }

    }

    private void reDraw(Event event) {
        var cols = event.field().getColumnSize();
        var rows = event.field().getRowSize();
        var field = event.field();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                var el = field.getElementByCoords(new Dot(i, j));
                if (el.isOpened()) {
                    buttons[i][j].setText(String.valueOf(el.getBombsAroundCount()));
                    buttons[i][j].setEnabled(false);
                } else {
                    buttons[i][j].setText("*");
                }
            }
        }
    }

    static class FieldElementsGrid extends JPanel {
        public FieldElementsGrid(int columnSize, int rowSize, FieldElement[][] buttons) {
            setLayout(new GridLayout(columnSize, rowSize, 3, 3));
            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[row].length; col++) {
                    FieldElement button = new FieldElement("*", new Dot(col, row));
                    add(button);
                    buttons[row][col] = button;
                }
            }

        }
    }
}