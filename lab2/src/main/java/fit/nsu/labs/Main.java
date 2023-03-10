package fit.nsu.labs;

import fit.nsu.labs.controllers.MineSweeperExecutor;
import fit.nsu.labs.views.Console;

public class Main {
    public static void main(String[] args) {
        var executor = new MineSweeperExecutor();
        executor.startGame(2, 2, 1, new Console());
    }
}