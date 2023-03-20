package fit.nsu.labs.controllers;

import fit.nsu.labs.exceptions.BombOpen;
import fit.nsu.labs.exceptions.InvalidArgument;
import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;
import fit.nsu.labs.views.MineSweeperViewer;

public class MineSweeperExecutor {
    private final int columnSize;
    private final int rowSize;
    private final int bombsCount;

    public MineSweeperExecutor(int columnSize, int rowSize, int bombsCount) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        this.bombsCount = bombsCount;
    }

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

    public void startGame(Class<? extends MineSweeperViewer> viewerClass) {
        int boardElementsCount = columnSize * rowSize;
        validate(boardElementsCount, bombsCount);

        var field = new GameField(columnSize, rowSize, bombsCount);
        try {
            var view = viewerClass.getConstructor(GameField.class, int.class, int.class).newInstance(field, columnSize, rowSize);

            while (true) {
                System.out.println("Asd");
                view.reDrawField();
                try {

                    var clickedButton = view.clickButton();
                    System.out.println("Adsad");
                    System.out.println(clickedButton);
                    if (field.isOpened(clickedButton)) {
                        System.err.println("this field already opened");
                        continue;
                    }

                    field.openElement(clickedButton);
                } catch (BombOpen ignored) {
                    System.out.println("Asd");
                    System.out.println("Boom!! Game finished");
                    break;
                } catch (IndexOutOfBoundsException ignored) {
                    System.err.println("Current input coords invalid");
                } catch (NullPointerException e) {
                    throw new InvalidArgument("Clicked button must be not null!");
                }

                var openedFieldsCount = getOpenedFieldsCount(field, columnSize, rowSize);
                if (checkWinState(bombsCount, boardElementsCount, openedFieldsCount)) {
                    System.out.println("You win!!!");
                    view.reDrawField();
                    break;
                }
            }


        } catch (ReflectiveOperationException e) {
            throw new InvalidArgument("Bad viewer class", e);
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
