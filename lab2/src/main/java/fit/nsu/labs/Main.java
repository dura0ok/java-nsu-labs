package fit.nsu.labs;

import fit.nsu.labs.controllers.CliExecutor;
import fit.nsu.labs.views.Console;

public class Main {
    public static void main(String[] args) {
        var executor = new CliExecutor();
        executor.startGame(2, 2, 1, new Console());
    }
}