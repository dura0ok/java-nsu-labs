package fit.nsu.labs.controllers;

import fit.nsu.labs.exceptions.BombOpen;
import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;
import fit.nsu.labs.views.Viewer;

public class MineSweeperExecutor {


    private static void validate(int boardElementsCount, int bombsCount) {
        if (bombsCount <= 0) {
            throw new InvalidArgument("Bombs counter must be positive");
        }

        if (bombsCount > boardElementsCount) {
            throw new InvalidArgument("bombs counter must be less-equal field size");
        }
    }

    private static boolean checkWinState(int bombsCount, int boardElementsCount, int openedFieldsCount) {
        return boardElementsCount - bombsCount == openedFieldsCount;
    }

    public void startGame(int height, int width, int bombsCount, Viewer view) {
        int boardElementsCount = height * width;
        validate(boardElementsCount, bombsCount);

        var field = new GameField(height, width, bombsCount);
        while (true) {
            view.showGameTable(field, height, width);
            try {
                var clickedButton = view.clickButton();
                if (field.isOpened(clickedButton)) {
                    System.err.println("this field already opened");
                    continue;
                }

                field.openElement(clickedButton);
            } catch (BombOpen ignored) {
                System.out.println("Booom!! Game finished");
                break;
            } catch (IndexOutOfBoundsException ignored) {
                System.err.println("Current input coords invalid");
            }

            var openedFieldsCount = getOpenedFieldsCount(field, height, width);
            if (checkWinState(bombsCount, boardElementsCount, openedFieldsCount)) {
                System.out.println("You win!!!");
                view.showGameTable(field, height, width);
                break;
            }
        }
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
