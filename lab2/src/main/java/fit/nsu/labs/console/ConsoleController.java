package fit.nsu.labs.console;

import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.exceptions.MineSweeperGameException;
import fit.nsu.labs.model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleController {
    private final Scanner scanner = new Scanner(System.in);
    private GameField model;

    public ConsoleController() {
        try {
            System.out.print("Enter name: ");
            var name = scanner.nextLine();

            if (name.isEmpty()) {
                throw new MineSweeperGameException("invalid name");
            }

            System.out.println("Menu");
            System.out.println("1 - easy");
            System.out.println("2 - medium");
            System.out.println("3 - hard");
            System.out.println("4 - custom");
            System.out.println("5 - records");

            int menuChoice = inputMenuChoice();

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

                    System.out.print("Enter cols: ");
                    var columns = scanner.nextInt();

                    System.out.print("Enter rows: ");
                    var rows = scanner.nextInt();

                    System.out.print("Enter bombs counter: ");
                    var bombsCounter = scanner.nextInt();

                    System.out.print("Enter flags counter: ");
                    var flagsCounter = scanner.nextInt();
                    model = new GameField(new GameSettings(columns, rows, bombsCounter, flagsCounter, GameLevels.CUSTOM), name);

                }
                case 5 -> {
                    System.out.print("1 - Easy, 2 - Medium, 3 - Hard, 4 - Custom: ");
                    var levelChoice = scanner.nextInt();
                    GameLevels level = null;
                    switch (levelChoice) {
                        case 1 -> level = GameLevels.EASY;
                        case 2 -> level = GameLevels.MEDIUM;
                        case 3 -> level = GameLevels.HARD;
                        case 4 -> level = GameLevels.CUSTOM;
                        default -> System.out.println("Something went wrong");
                    }
                    var manager = new RecordsManager();
                    var data = manager.readRecords(level);
                    for (var el : data) {
                        System.out.println(el);
                    }
                }
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

        // TODO: controller creates view -> not ideal.
        // TODO: at least pass it as a parameter in constructor.
        // TODO: even better to make them completely separated (not that hard for the console UI)
        model.registerObserver(new ConsoleViewer());
        this.model.startGame();
        while (model.getState() != GameField.GameState.GAME_OVER) {
            try {
                System.out.println("Enter type command(dot - 0, flag - 1, time - 2): ");
                var type = scanner.nextInt();

                if (type == 2) {
                    System.out.println("Elapsed time: " + model.getElapsed());
                    continue;
                }

                if (type != 0 && type != 1) {
                    throw new InvalidArgument("invalid command type");
                }


                System.out.print("Enter x: ");
                var x = scanner.nextInt();
                System.out.print("Enter y: ");
                var y = scanner.nextInt();


                if (type == 0) {
                    model.click(new Dot(x, y));
                }

                if (type == 1) {
                    model.updateFlag(new Dot(x, y));
                }

            } catch (InvalidArgument e) {
                System.err.println(e.getMessage());
            } catch (InputMismatchException ignored) {
                System.err.println("Incorrect input");
                scanner.nextLine();
            }

        }
    }


    int inputMenuChoice() throws InvalidArgument {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new InvalidArgument("invalid menu choice", e);
        }
    }
}
