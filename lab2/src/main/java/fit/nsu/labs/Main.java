package fit.nsu.labs;

import fit.nsu.labs.controllers.gameExecutor;
import fit.nsu.labs.views.Console;

public class Main {
    public static void main(String[] args) {
        var executor = new gameExecutor();
        executor.startGame(2, 2, 1, new Console());
    }
}