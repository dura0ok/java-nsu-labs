package fit.nsu.labs.console;

import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

import java.util.Scanner;

public class MainController {
    private final GameField model;
    private final Scanner scanner = new Scanner(System.in);

    public MainController(int columnSize, int rowSize, int bombsCount) {
        this.model = new GameField(columnSize, rowSize, bombsCount, new ConsoleViewer());
    }

    public static void main(String[] args) {
        var game = new MainController(10, 10, 1);
        game.startGame();
    }

    public void startGame() {
        while (model.getState() != GameField.GameState.GAME_OVER) {
            System.out.println("Enter x: ");
            var x = scanner.nextInt();
            System.out.println("Enter y: ");
            var y = scanner.nextInt();
            model.click(new Dot(x, y));
            System.out.println(model.getState());
        }
    }
}
