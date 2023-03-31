package fit.nsu.labs.graphics;

import fit.nsu.labs.model.GameField;
import fit.nsu.labs.model.GameLevels;
import fit.nsu.labs.model.GameSettings;
import fit.nsu.labs.model.RecordsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MenuFrame extends JFrame {


    private String playerName = null;

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

        var scoresButton = new JButton("Show scores");
        scoresButton.addActionListener(e -> {
            var frame = new JFrame("High Scores");
            String[] options = {GameLevels.EASY.name(), GameLevels.MEDIUM.name(), GameLevels.HARD.name(), GameLevels.CUSTOM.name()};
            String selectedOption = (String) JOptionPane.showInputDialog(null, "Choose level:", "Options",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            var recordsHandler = new RecordsManager();
            var easy = recordsHandler.readRecords(GameLevels.valueOf(selectedOption));
            var table = new JTable(new RecordsTable(easy, GameLevels.valueOf(selectedOption)));
            frame.add(new JScrollPane(table));
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
        buttonsContainer.add(scoresButton);

        var helpButton = new JButton("Help");
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Help!"));
        buttonsContainer.add(helpButton);

        for (var button : buttonsContainer) {
            buttonsPanel.add(button, container);
            container.insets = new Insets(10, 0, 0, 0);
        }


        add(buttonsPanel, container);

        setVisible(true);
        setSize(500, 500);
        setLocationRelativeTo(null);

        while (playerName == null || playerName.isEmpty()) {
            playerName = JOptionPane.showInputDialog(null, "Enter name");
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public String getPlayerName() {
        return playerName;
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
            model = new GameField(easyLevel, menu.getPlayerName());
        }

        if (e.getActionCommand().equals(String.valueOf(GameLevels.MEDIUM))) {
            var mediumLevel = GameSettings.getMediumLevel();
            model = new GameField(mediumLevel, menu.getPlayerName());
        }

        if (e.getActionCommand().equals(String.valueOf(GameLevels.HARD))) {
            var hardLevel = GameSettings.getHardLevel();
            model = new GameField(hardLevel, menu.getPlayerName());
        }


        if (e.getActionCommand().equals(String.valueOf(GameLevels.CUSTOM))) {
            try {
                int columns = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter cols"));
                int rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter rows"));
                int bombsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter bombs counter"));
                int flagsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter flags counter"));

                model = new GameField(new GameSettings(columns, rows, bombsCounter, flagsCounter, GameLevels.CUSTOM), menu.getPlayerName());
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
