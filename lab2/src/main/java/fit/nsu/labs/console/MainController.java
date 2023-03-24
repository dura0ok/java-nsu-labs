package fit.nsu.labs.console;

import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import java.util.Scanner;

public class MainController {
    private final GameField model;
    private final Scanner scanner = new Scanner(System.in);

    public MainController(int columnSize, int rowSize, int bombsCount) {
        this.model = new GameField(columnSize, rowSize, bombsCount);
        model.registerObserver(new ConsoleViewer());

    }

    public static void main(String[] args) {
        var game = new MainController(10, 10, 2);
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
                model.click(new Dot(x, y));
            } catch (InvalidArgument e) {
                System.err.println(e.getMessage());
            }

            //System.out.println(model.getState());
        }
    }
}
