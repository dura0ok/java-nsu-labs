package fit.nsu.labs;

import fit.nsu.labs.controllers.MineSweeperExecutor;
import fit.nsu.labs.views.Graphics;

public class Main {
    public static void main(String[] args) {
        var height = 2;
        var width = 2;
        var bombsCount = 1;

        var executor = new MineSweeperExecutor(height, width, bombsCount);
        executor.startGame(Graphics.class);
    }
}