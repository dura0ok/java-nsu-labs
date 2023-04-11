package fit.nsu.labs.console;

import fit.nsu.labs.exceptions.MineSweeperGameException;
import fit.nsu.labs.model.*;

public class ConsoleController {

    private final ConsoleViewer view;
    private GameField model;

    public ConsoleController(ConsoleViewer view) {
        this.view = view;
        try {
            var name = view.inputLine("Enter name: ");

            if (name.isEmpty()) {
                throw new MineSweeperGameException("invalid name");
            }

            view.printMenu();

            int menuChoice = view.inputNumber("Enter menu choice: ");

            switch (menuChoice) {
                case 1 -> {
                    var easyLevel = GameSettings.getEasyLevel();
                    model = new GameField(easyLevel, name);
                }
                case 2 -> {
                    var mediumLevel = GameSettings.getMediumLevel();
                    model = new GameField(mediumLevel, name);
                }
                case 3 -> {
                    var hardLevel = GameSettings.getHardLevel();
                    model = new GameField(hardLevel, name);
                }
                case 4 -> {

                    var columns = view.inputNumber("Enter cols: ");

                    var rows = view.inputNumber("Enter rows: ");

                    var bombsCounter = view.inputNumber("Enter bombs counter: ");

                    var flagsCounter = view.inputNumber("Enter limit flags: ");
                    model = new GameField(new GameSettings(columns, rows, bombsCounter, flagsCounter, GameLevels.CUSTOM), name);

                }
                case 5 -> {
                    var levelChoice = view.inputNumber("1 - Easy, 2 - Medium, 3 - Hard, 4 - Custom: ");
                    GameLevels level = null;
                    switch (levelChoice) {
                        case 1 -> level = GameLevels.EASY;
                        case 2 -> level = GameLevels.MEDIUM;
                        case 3 -> level = GameLevels.HARD;
                        case 4 -> level = GameLevels.CUSTOM;
                        case 5 -> System.exit(0);
                        default -> System.out.println("Something went wrong");
                    }
                    var manager = new RecordsManager();
                    var data = manager.readRecords(level);
                    for (var el : data) {
                        System.out.println(el);
                    }
                }
                case 6 -> System.exit(0);
                default -> throw new IllegalStateException("Unexpected value: " + menuChoice);
            }

        } catch (MineSweeperGameException e) {
            System.err.println(e.getMessage());
        }
    }


    public void startGame() {
        if (model == null) {
            return;
        }

        model.registerObserver(view);
        this.model.startGame();
        while (model.getState() != GameField.GameState.GAME_OVER) {
            try {
                var type = view.inputNumber("Enter type command(dot - 0, flag - 1, time - 2, exit - 3): ");

                if (type == 2) {
                    System.out.println("Elapsed time: " + model.getElapsed());
                    continue;
                }

                if (type != 0 && type != 1) {
                    throw new IllegalArgumentException("invalid command type");
                }


                var x = view.inputNumber("Enter x: ");
                var y = view.inputNumber("Enter y: ");


                if (type == 0) {
                    model.click(new Dot(x, y));
                }

                if (type == 1) {
                    model.updateFlag(new Dot(x, y));
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }


}
