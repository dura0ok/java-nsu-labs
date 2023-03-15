package ru.nsu.neretin.lab1503;


import controllers.GameController;
import model.GameModel;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var model = new GameModel();
        var controller = new GameController(model);
        controller.startGame();
    }
}