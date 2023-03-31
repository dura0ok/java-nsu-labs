package fit.nsu.labs.console;

import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.exceptions.MineSweeperGameException;
import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;
import fit.nsu.labs.model.GameLevels;

import java.util.Scanner;

public class ConsoleController {
    private final GameField model;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(int columnSize, int rowSize, int bombsCount) {
        System.out.print("Enter name: ");
        var name = scanner.nextLine();
        if (name.isEmpty()) {
            throw new MineSweeperGameException("invalid name");
        }
        this.model = new GameField(columnSize, rowSize, bombsCount, 10, GameLevels.EASY, name);
        model.registerObserver(new ConsoleViewer());

    }

    public static void main(String[] args) {
        var game = new ConsoleController(2, 2, 1);
        game.startGame();

    }

    public void startGame() {
        this.model.startGame();
        while (model.getState() != GameField.GameState.GAME_OVER) {
            try {
                System.out.print("Enter type command(dot - 0, flag - 1, time - 2): ");
                var type = scanner.nextInt();

                if(type == 2){
                    System.out.println("Elapsed time: " + model.getElapsed());
                    continue;
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
            }

            //System.out.println(model.getState());
        }
    }
}
