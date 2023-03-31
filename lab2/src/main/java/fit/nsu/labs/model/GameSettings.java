package fit.nsu.labs.model;

public record GameSettings(int cols, int rows, int bombsCount, int flagsCount, GameLevels level) {
    public static GameSettings getEasyLevel() {
        return new GameSettings(2, 2, 2, 2, GameLevels.EASY);
    }

    public static GameSettings getMediumLevel() {
        return new GameSettings(5, 5, 10, 10, GameLevels.MEDIUM);
    }

    public static GameSettings getHardLevel() {
        return new GameSettings(10, 10, 40, 40, GameLevels.HARD);
    }
}
