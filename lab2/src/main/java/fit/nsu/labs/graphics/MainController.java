package fit.nsu.labs.graphics;

import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener {
    private final GameField model;
    private final JFrame frame = new JFrame();
    private final FieldElement[][] buttons;

    public MainController(int columnSize, int rowSize, int bombsCount) {
        buttons = new FieldElement[rowSize][columnSize];
        this.model = new GameField(columnSize, rowSize, bombsCount, new GraphicsViewer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new FieldElementsGrid(columnSize, rowSize, buttons));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        addActionListener(buttons);
    }

    private void addActionListener(FieldElement[][] buttons) {
        for (JButton[] jButtons : buttons) {
            for (JButton jButton : jButtons) {
                jButton.addActionListener(this);
            }
        }
    }

    void startGame(){
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        FieldElement sourceBtn = (FieldElement) e.getSource();
        var x = Integer.parseInt(String.valueOf(sourceBtn.getDot().x()));
        var y = Integer.parseInt(String.valueOf(sourceBtn.getDot().y()));

        var dot = new Dot(x,y);


        String message = "Button pressed: " + actionCommand + " " + x + " " + y;
        //JOptionPane.showMessageDialog(sourceBtn, message, String.valueOf(model.getElementByCoords(dot).getBombsAroundCount()), JOptionPane.PLAIN_MESSAGE);
        sourceBtn.setText(String.valueOf(model.getElementByCoords(dot).getBombsAroundCount()));
        sourceBtn.setEnabled(false);

        model.click(dot);
    }


    class FieldElementsGrid extends JPanel {
        public FieldElementsGrid(int columnSize, int rowSize, FieldElement[][] buttons) {
            setLayout(new GridLayout(columnSize, rowSize, 3, 3));
            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[row].length; col++) {
                    FieldElement button = new FieldElement("row", new Dot(col, row));
                    add(button);
                    buttons[row][col] = button;
                }
            }

        }
    }


    public static void main(String[] args) {
        var game = new MainController(10, 10, 1);
        game.startGame();
    }
}