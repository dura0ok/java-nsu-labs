package fit.nsu.labs.graphics;

import fit.nsu.labs.model.GameField;
import fit.nsu.labs.model.GameLevels;
import fit.nsu.labs.model.GameSettings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuFrame extends JFrame {
    MenuFrame() {
        JLabel name = new JLabel("Minesweeper game");
        name.setFont(new Font("Arial", Font.BOLD, 30));

        setLayout(new GridBagLayout());


        GridBagConstraints container = new GridBagConstraints();
        container.gridwidth = GridBagConstraints.REMAINDER;
        container.anchor = GridBagConstraints.NORTH;

        add(name, container);

        container.anchor = GridBagConstraints.CENTER;
        container.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        container.weightx = 1;

        List<JButton> buttonsContainer = new ArrayList<>();
        var easyButton = new JButton("Easy Level");
        easyButton.setActionCommand(String.valueOf(GameLevels.EASY));
        easyButton.addActionListener(new MenuHandler());
        buttonsContainer.add(easyButton);

        var mediumButton = new JButton("Medium Level");
        mediumButton.setActionCommand(String.valueOf(GameLevels.MEDIUM));
        mediumButton.addActionListener(new MenuHandler());
        buttonsContainer.add(mediumButton);

        var hardButton = new JButton("Hard Level");
        hardButton.setActionCommand(String.valueOf(GameLevels.HARD));
        hardButton.addActionListener(new MenuHandler());
        buttonsContainer.add(hardButton);

        var customButton = new JButton("Custom Level");
        customButton.setActionCommand(String.valueOf(GameLevels.CUSTOM));
        customButton.addActionListener(new MenuHandler());
        buttonsContainer.add(customButton);

        buttonsContainer.add(new JButton("Custom Game"));
        buttonsContainer.add(new JButton("Show scores"));
        buttonsContainer.add(new JButton("Help"));

        for (var button : buttonsContainer) {
            buttonsPanel.add(button, container);
            container.insets = new Insets(10, 0, 0, 0);
        }


        add(buttonsPanel, container);

        setVisible(true);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


class MenuHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        GameField model = null;
        if (e.getActionCommand().equals(String.valueOf(GameLevels.EASY))) {
            var easyLevel = GameSettings.getEasyLevel();
            model = new GameField(easyLevel.cols(), easyLevel.rows(), easyLevel.bombsCount(), easyLevel.flagsCount());
        }

        if (e.getActionCommand().equals(String.valueOf(GameLevels.MEDIUM))) {
            var mediumLevel = GameSettings.getHardLevel();
            model = new GameField(mediumLevel.cols(), mediumLevel.rows(), mediumLevel.bombsCount(), mediumLevel.flagsCount());
        }

        if (e.getActionCommand().equals(String.valueOf(GameLevels.HARD))) {
            var hardLevel = GameSettings.getHardLevel();
            model = new GameField(hardLevel.cols(), hardLevel.rows(), hardLevel.bombsCount(), hardLevel.flagsCount());
        }

        if (e.getActionCommand().equals(String.valueOf(GameLevels.CUSTOM))) {
            int columns = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter cols"));
            int rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter rows"));
            int bombsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter bombs counter"));
            int flagsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter flags counter"));

            model = new GameField(columns, rows, bombsCounter, flagsCounter);
        }


        if (model != null) {
            var graphicsView = new GraphicsViewer(model);
            model.registerObserver(graphicsView);
            model.startGame();
            graphicsView.setVisible(true);
        }

    }
}
