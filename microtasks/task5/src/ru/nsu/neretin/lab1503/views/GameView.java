package views;

import model.GameModel;
import ru.nsu.neretin.lab1503.model.Record;

import java.util.ArrayList;

public class GameView {
    private final GameModel model;


    public GameView(GameModel model) {
        this.model = model;
    }

    public void printNumber() {
        System.out.println("Number: " + model.getNumber());
    }

    public void printCorrectAttempt() {
        System.out.println("Your attempt is correct. Your current points: " + model.getPoints());
    }

    public void printIncorrectAttempt() {
        System.out.println("Your attempt is not correct");
    }

    public void printRecords(ArrayList<Record> records) {
        for (var record : records) {
            System.out.printf("Record: %s - %d%n", record.getName(), record.getPoints());
        }
    }

    public void incorrectInput() {
        System.out.println("Incorrect input");
    }

}
