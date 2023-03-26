package fit.nsu.labs.graphics;

import fit.nsu.labs.model.Event;
import fit.nsu.labs.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.JOptionPane.showMessageDialog;


public class GraphicsViewer extends JFrame implements Observer {

    private static final String closedIconName = "closed.png";
    private static final String flaggedIconName = "flagged.png";
    private final FieldElement[][] buttons;
    private final GameField model;
    private final MainController controller;

    public GraphicsViewer(GameField model) {
        this.model = model;
        controller = new MainController(this.model);
        buttons = new FieldElement[model.getColumnSize()][model.getRowSize()];
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        int size = model.getColumnSize() * model.getRowSize();
        setSize(size, size);
        setResizable(false);

        setContentPane(new FieldElementsGrid(model.getColumnSize(), model.getRowSize(), buttons));
        addActionListener(buttons);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);


        pack();
    }


    private void addActionListener(FieldElement[][] buttons) {
        for (JButton[] jButtons : buttons) {
            for (JButton jButton : jButtons) {
                jButton.addMouseListener(controller);
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
            return;
        }
        if (event.type().equals(EventType.ALREADY_CLICKED)) {
            showMessageDialog(null, "Already opened");
            return;
        }


        if (event.type().equals(EventType.FLAG_STATE_UPDATE)) {
            reDraw(event);
            return;
        }
    }

    private void reDraw(Event event) {
        var cols = event.field().getColumnSize();
        var rows = event.field().getRowSize();
        var field = event.field();

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                var el = field.getElementByCoords(new Dot(i, j));

                ImageIcon icon;
                var state = model.getState();
                var imageName = String.format("num%d.png", el.getBombsAroundCount());
                if (state.equals(GameField.GameState.GAME_OVER) || el.isOpened()) {
                    if (state.equals(GameField.GameState.GAME_OVER) && el.isBomb()) {
                        imageName = "bomb.png";
                        buttons[i][j].setEnabled(false);
                    }

                    if (state.equals(GameField.GameState.GAME_OVER) && !el.isOpened()) {
                        el.open();
                    }


                    //buttons[i][j].setText(String.valueOf(el.getBombsAroundCount()));
                    //buttons[i][j].setEnabled(false);
                } else {

                    imageName = closedIconName;
                    if(el.isFlagged()){
                        imageName = flaggedIconName;
                    }
                }
                icon = new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemResource(imageName)));
                buttons[i][j].setIcon(icon);
            }
        }
    }

    static class FieldElementsGrid extends JPanel {
        public FieldElementsGrid(int columnSize, int rowSize, FieldElement[][] buttons) {
            setLayout(new GridLayout(columnSize, rowSize, 3, 3));
            setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[row].length; col++) {
                    FieldElement button = new FieldElement("", new Dot(col, row));
                    var icon = new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemResource(closedIconName)));
                    //Image image = icon.getImage().getScaledInstance(icon.getImage().getWidth(this), icon.getImage().getWidth(this), Image.SCALE_SMOOTH);
                    //ImageIcon resizedIcon = new ImageIcon(image);

                    //icon.setImage();
                    button.setIcon(icon);
                    button.setPreferredSize(new Dimension(50, 50));

                    add(button);

                    buttons[row][col] = button;
                }
            }

        }
    }
}