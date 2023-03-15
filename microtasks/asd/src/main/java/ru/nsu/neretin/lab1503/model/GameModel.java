package ru.nsu.neretin.lab1503.model;

import java.io.IOException;

public class GameModel {

    private int number;

    public int getPoints() {
        return points;
    }

    private int points = 0;

    public GameModel() {
        number = generateNumber();
    }

    private int generateNumber() {
        number = getRandomNumber(0, 10000);
        return number;
    }

    public boolean isCorrectAttempt(int guess){
        boolean res = guess == number;
        if(res){
            generateNumber();
            points++;

        }
        return res;
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
