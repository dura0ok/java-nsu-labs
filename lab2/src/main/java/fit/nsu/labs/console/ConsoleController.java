package fit.nsu.labs.console;

import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import java.util.Scanner;

public class ConsoleController {
    private final GameField model;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(int columnSize, int rowSize, int bombsCount) {
        this.model = new GameField(columnSize, rowSize, bombsCount, 10);
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
                System.out.println("Enter x: ");
                var x = scanner.nextInt();
                System.out.println("Enter y: ");
                var y = scanner.nextInt();

                System.out.println("Enter type(dot - 0, flag - 1)");
                var type = scanner.nextInt();
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
