package ru.nsu.neretin.lab1503.model;

public class Record {
    private final String name;
    private final int points;

    public Record(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}
