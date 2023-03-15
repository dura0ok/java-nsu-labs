package ru.nsu.neretin.lab1503.controllers;

import ru.nsu.neretin.lab1503.model.GameModel;
import ru.nsu.neretin.lab1503.model.RecordsHandler;
import ru.nsu.neretin.lab1503.views.GameView;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController {
    private final GameModel model;
    private final Scanner scanner = new Scanner(System.in);
    private final GameView view;
    private final int exitNumber = -1;

    private final RecordsHandler handler = new RecordsHandler();

    public GameController(GameModel model) throws IOException {
        this.model = model;
        view = new GameView(model);
    }

    public void startGame(){
        System.out.print("Enter your name: ");
        var name = scanner.nextLine();
        try{
            while(true){
                System.out.print("Enter your attempt number: ");
                var attemptNumber = scanner.nextInt();

                if(attemptNumber == exitNumber){
                    handler.pushRecord(name, model.getPoints());
                    view.printRecords(handler.getRecords());
                    break;
                }

                if(model.isCorrectAttempt(attemptNumber)){
                    view.printCorrectAttempt();
                }else{
                    view.printIncorrectAttempt();
                }
            }
        }catch (InputMismatchException e){
            // todo
            e.printStackTrace();
            scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
