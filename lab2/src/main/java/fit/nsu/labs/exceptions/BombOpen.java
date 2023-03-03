package fit.nsu.labs.exceptions;

public class BombOpen extends MineSweeperGameException {

    public BombOpen() {
        super("Bomb opened. Game finished");
    }
}
