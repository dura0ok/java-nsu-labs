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
        easyButton.addActionListener(new MenuHandler(this));
        buttonsContainer.add(easyButton);

        var mediumButton = new JButton("Medium Level");
        mediumButton.setActionCommand(String.valueOf(GameLevels.MEDIUM));
        mediumButton.addActionListener(new MenuHandler(this));
        buttonsContainer.add(mediumButton);

        var hardButton = new JButton("Hard Level");
        hardButton.setActionCommand(String.valueOf(GameLevels.HARD));
        hardButton.addActionListener(new MenuHandler(this));
        buttonsContainer.add(hardButton);

        var customButton = new JButton("Custom Game");
        customButton.setActionCommand(String.valueOf(GameLevels.CUSTOM));
        customButton.addActionListener(new MenuHandler(this));
        buttonsContainer.add(customButton);

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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}


class MenuHandler implements ActionListener {
    private final MenuFrame menu;

    public MenuHandler(MenuFrame menuFrame) {
        menu = menuFrame;
    }

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
            try {
                int columns = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter cols"));
                int rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter rows"));
                int bombsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter bombs counter"));
                int flagsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter flags counter"));

                model = new GameField(columns, rows, bombsCounter, flagsCounter);
            } catch (NumberFormatException ignored) {
            }
        }


        if (model != null) {

            var graphicsView = new GraphicsViewer(model);

            model.registerObserver(graphicsView);
            model.startGame();
            graphicsView.setVisible(true);
            menu.setVisible(false);
            graphicsView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    menu.setVisible(true);
                }


            });

            menu.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    menu.dispose();
                }


            });
        }

    }
}
