package fit.nsu.labs.graphics;

import fit.nsu.labs.model.GameField;
import fit.nsu.labs.model.GameLevels;
import fit.nsu.labs.model.GameSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                int flagsCounter = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter limit flags"));

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
