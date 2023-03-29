package fit.nsu.labs.model;

import java.io.Serializable;

public class Record implements Serializable {
    private final String name;
    private final long secondsTime;

    private final GameLevels levels;

    public Record(String name, long secondsTime, GameLevels levels) {
        this.name = name;
        this.secondsTime = secondsTime;
        this.levels = levels;
    }

    @Override
    public String toString() {
        return String.format("%s %d %s\n", name, secondsTime, levels);
    }
}
