package fit.nsu.labs.model;

import java.io.Serializable;

public class Record implements Serializable {
    private final String name;
    private final long secondsTime;


    public Record(String name, long secondsTime) {
        this.name = name;
        this.secondsTime = secondsTime;
    }

    @Override
    public String toString() {
        return String.format("%s %d\n", name, secondsTime);
    }
}
