package fit.nsu.labs;

import fit.nsu.labs.controllers.MineSweeperExecutor;
import fit.nsu.labs.views.Console;

public class Main {
    public static void main(String[] args) {
        var executor = new MineSweeperExecutor();
        var height = 2;
        var width = 2;
        var bombsCount = 1;
        executor.startGame(height, width, bombsCount, new Console());
    }
}