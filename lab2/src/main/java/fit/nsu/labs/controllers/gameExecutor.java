package fit.nsu.labs.controllers;

import fit.nsu.labs.exceptions.BombOpen;
import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;
import fit.nsu.labs.views.Viewer;

import java.util.Scanner;

public class gameExecutor {


    public void startGame(int height, int width, int bombsCount, Viewer view) {
        int boardElementsCount = height * width;

        var scanner = new Scanner(System.in);
        if (bombsCount <= 0 || bombsCount > width * height) {
            throw new InvalidArgument("bombs counter");
        }


        var field = new GameField(height, width, bombsCount);
        while (true) {
            if (boardElementsCount - bombsCount == getOpenedFieldsCount(field, height, width)) {
                System.out.println("You win!!!");
                view.showGameTable(field, height, width);
                break;
            }

            view.showGameTable(field, height, width);
            System.out.println("Enter x: ");
            var x = scanner.nextInt();
            System.out.println("Enter y: ");
            var y = scanner.nextInt();
            try {
                var dot = new Dot(x, y);
                if (field.isOpened(dot)) {
                    System.err.println("this field already");
                    continue;
                }

                field.openElement(dot);
            } catch (BombOpen ignored) {
                System.out.println("Booom!! Game finished");
                break;
            } catch (IndexOutOfBoundsException ignored) {
                System.err.println("Current input coords invalid");
            }

        }
//

//        field.printBoard();

    }

    private int getOpenedFieldsCount(GameField field, int columnSize, int rowSize) {
        int res = 0;
        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                if (field.isOpened(new Dot(i, j))) {
                    res++;
                }
            }
        }
        return res;
    }
}
