package fit.nsu.labs.model;


import fit.nsu.labs.exceptions.BombOpen;

import java.util.ArrayList;
import java.util.HashSet;

import static fit.nsu.labs.Utils.getRandomNumber;

public class GameField {
    private final BoardElement[][] board;
    private final HashSet<BoardElement> bombs = new HashSet<>();

    private final int columnSize;
    private final int rowSize;


    public GameField(int columnSize, int rowSize, int bombsCounter) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        board = new BoardElement[columnSize][rowSize];

        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                board[i][j] = new BoardElement(new Dot(i, j));
            }
        }

        generateBombs(bombsCounter);
        updateBombsAroundCount();
    }


    ArrayList<BoardElement> getNeighbours(Dot dot) {
        var res = new ArrayList<BoardElement>();
        var dotX = dot.getX();
        var dotY = dot.getY();
        try {
            for (int y = dotY - 1; y <= dotY + 1; y++) {
                for (int x = dotX - 1; x <= dotX + 1; x++) {
                    if (y < 0 || y >= rowSize) {
                        continue;
                    }
                    if (x < 0 || x >= columnSize) {
                        continue;
                    }
                    if (x == dotX && y == dotY) {
                        continue;
                    }
                    res.add(getElementByCoords(new Dot(x, y)));
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return res;
    }

    private void updateBombsAroundCount() {
        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                int local_counter = 0;
                var neighbours = getNeighbours(new Dot(i, j));
                for (var neighbour : neighbours) {
                    if (neighbour.isBomb()) {
                        local_counter += 1;
                    }
                }
                board[i][j].setBombsAroundCount(local_counter);
            }
        }
    }

    private void generateBombs(int bombsCounter) {
        while (bombs.size() != bombsCounter) {
            var element = getElementByCoords(generateRandomDot());
            System.out.println("Bomb coords " + element.getBoardCoords());
            if (!element.isBomb()) {
                bombs.add(element);
                element.makeBombType();
            }
        }
    }

    private Dot generateRandomDot() {
        int x = getRandomNumber(0, columnSize);
        int y = getRandomNumber(0, rowSize);
        return new Dot(x, y);
    }

    public BoardElement getElementByCoords(Dot coords) {
        return board[coords.getX()][coords.getY()];
    }

    public void openElement(Dot dot) {
        if (getElementByCoords(dot).isBomb()) {
            throw new BombOpen();
        }

        getElementByCoords(dot).open();

        openAroundArea(dot);

    }

    private void openAroundArea(Dot dot) {
        if (getElementByCoords(dot).getBombsAroundCount() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    var newPoint = new Dot(dot.getX() + i, dot.getY() + j);
                    if (newPoint.getY() > rowSize || newPoint.getX() > columnSize) {
                        continue;
                    }

                    if (!getElementByCoords(newPoint).isOpened()) {
                        openElement(newPoint);
                    }
                }
            }
        }
    }


    public boolean isOpened(Dot dot) {
        return getElementByCoords(dot).isOpened();
    }
}


