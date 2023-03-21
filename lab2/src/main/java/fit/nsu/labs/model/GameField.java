package fit.nsu.labs.model;

import fit.nsu.labs.exceptions.InvalidArgument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static fit.nsu.labs.utils.Random.getRandomNumber;


public class GameField implements Observable {
    private final BoardElement[][] board;
    private final HashSet<Dot> bombs = new HashSet<Dot>();

    private final int columnSize;
    private final int rowSize;

    private final int boardElementsCount;
    private final List<Observer> observers = new ArrayList<>();
    private int openedFieldsCount = 0;
    private GameField.GameState state;

    public GameField(int columnSize, int rowSize, int bombsCounter, Observer observer) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        boardElementsCount = columnSize * rowSize;
        validate(bombsCounter);

        board = new BoardElement[columnSize][rowSize];
        generateBombs(bombsCounter);
        initializeField();
        updateBombsAroundCount();

        state = GameState.RUNNING;

        registerObserver(observer);
        notifyObservers(new Event(EventType.REDRAW_REQUEST, this));
    }

    public GameState getState() {
        return state;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getRowSize() {
        return rowSize;
    }

    private void initializeField() {
        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                var currentDot = new Dot(i, j);
                if (bombs.contains(currentDot)) {
                    board[i][j] = new BoardElement(new Dot(i, j), BoardElement.BoardElementType.BOMB);
                    continue;
                }

                board[i][j] = new BoardElement(new Dot(i, j));
            }
        }
    }


    ArrayList<BoardElement> getNeighbours(Dot dot) {
        var res = new ArrayList<BoardElement>();
        var dotX = dot.x();
        var dotY = dot.y();
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
            var element = generateRandomDot();
            if (!bombs.contains(element)) {
                System.out.println("Bomb coords " + element);
            }
            bombs.add(element);
        }
    }

    private Dot generateRandomDot() {
        int x = getRandomNumber(0, columnSize);
        int y = getRandomNumber(0, rowSize);
        //return new Dot(x, y);
        return new Dot(3, 5);
    }

    public BoardElement getElementByCoords(Dot coords) {
        return board[coords.x()][coords.y()];
    }

    public void click(Dot dot) {
        if (getElementByCoords(dot).isOpened()) {
            notifyObservers(new Event(EventType.ALREADY_CLICKED, this));
            return;
        }

        if (getElementByCoords(dot).isBomb()) {
            //throw new RuntimeException("bomb");

            // todo :C
            state = GameState.GAME_OVER;
            notifyObservers(new Event(EventType.BOMB_OPENED, this));
            return;
        }

        openElement(dot);
        notifyObservers(new Event(EventType.REDRAW_REQUEST, this));
    }

    private void openElement(Dot dot) {
        getElementByCoords(dot).open();
        openedFieldsCount++;
        openAroundArea(dot);

        checkWinState();

    }

    private void openAroundArea(Dot dot) {
        if (getElementByCoords(dot).getBombsAroundCount() == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    var newPoint = new Dot(dot.x() + i, dot.y() + j);
                    if (newPoint.x() < 0 || newPoint.x() >= columnSize) {
                        continue;
                    }

                    if (newPoint.y() < 0 || newPoint.y() >= rowSize) {
                        continue;
                    }

                    if (getElementByCoords(newPoint).isOpened()) {
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

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.notification(event);
        }
    }

    private void checkWinState() {
        if (boardElementsCount - bombs.size() == openedFieldsCount) {
            state = GameState.GAME_OVER;
        }
    }

    private void validate(int bombsCount) {
        if (0 >= bombsCount) {
            throw new InvalidArgument("Bombs counter must be positive");
        }

        if (bombsCount > boardElementsCount) {
            throw new InvalidArgument("bombs counter must be less-equal field size");
        }
    }


    public enum GameState {
        RUNNING,
        GAME_OVER
    }

}


