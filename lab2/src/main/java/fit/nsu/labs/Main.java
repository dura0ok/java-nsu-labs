package fit.nsu.labs;

import fit.nsu.labs.controllers.gameExecutor;

public class Main {
    public static void main(String[] args) {
        var executor = new gameExecutor();
        executor.startGame(2, 2, 1);
    }
}