package fit.nsu.labs.model;

import fit.nsu.labs.exceptions.InvalidArgument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static fit.nsu.labs.utils.Random.getRandomNumber;


public class GameField implements Observable {
    private final BoardElement[][] board;
    private final HashSet<Dot> bombs = new HashSet<>();

    private final int columnSize;
    private final int rowSize;

    private final int boardElementsCount;
    private final List<Observer> observers = new ArrayList<>();
    private final int bombsCounter;
    private final GameTime timer = new GameTime(this);
    private int availableFlagsCounter;
    private int openedFieldsCount = 0;
    private GameField.GameState state;
    private ScheduledExecutorService executor;

    private ScheduledFuture<?> future;

    public GameField(int columnSize, int rowSize, int bombsCounter, int flaggedLimit) {
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        this.bombsCounter = bombsCounter;
        this.availableFlagsCounter = flaggedLimit;
        boardElementsCount = columnSize * rowSize;
        validate(bombsCounter);

        board = new BoardElement[columnSize][rowSize];
        generateBombs(bombsCounter);
        initializeField();
        updateBombsAroundCount();
        state = GameState.GAME_OVER;
    }

    public long getCurrentTimer() {
        return timer.getElapsed();
    }

    public int getAvailableFlagsCounter() {
        return availableFlagsCounter;
    }

    public void startGame() {
        state = GameState.RUNNING;
        executor = Executors.newScheduledThreadPool(1);
        timer.reset();
        future = executor.scheduleWithFixedDelay(timer, 0, 1, TimeUnit.SECONDS);
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


    public int getBombsCounter() {
        return bombs.size();
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
        while (getBombsCounter() != bombsCounter) {
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
        return new Dot(x, y);
        //return new Dot(0, 0);
    }

    public BoardElement getElementByCoords(Dot coords) {
        return board[coords.x()][coords.y()];
    }

    public void click(Dot dot) {

        if (dot.x() >= columnSize || dot.y() >= rowSize) {
            throw new InvalidArgument("Invalid clicked dot coords");
        }

        if (dot.x() < 0 || dot.y() < 0) {
            throw new InvalidArgument("Invalid clicked dot coords");
        }

        var el = getElementByCoords(dot);

        if (el.isFlagged()) {
            notifyObservers(new Event(EventType.ALREADY_FLAGGED, this));
            return;
        }

        if (isOpened(dot)) {
            System.out.println("ALREADY_CLICKED");
            notifyObservers(new Event(EventType.ALREADY_CLICKED, this));
            return;
        }

        if (isDotBomb(dot)) {
            System.out.println("throw bomb opened");
            notifyObservers(new Event(EventType.BOMB_OPENED, this));
            state = GameState.GAME_OVER;
            notifyObservers(new Event(EventType.REDRAW_REQUEST, this));

            future.cancel(true);
            executor.shutdownNow();
            return;
        }

        openElement(dot);
        checkWinState();
        notifyObservers(new Event(EventType.REDRAW_REQUEST, this));
    }

    private void openElement(Dot dot) {
        if (isOpened(dot) || isDotBomb(dot)) {
            return;
        }

        getElementByCoords(dot).open();
        openedFieldsCount++;
        openAroundArea(dot);
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

    public boolean isDotBomb(Dot dot) {
        return getElementByCoords(dot).isBomb();
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
        if (boardElementsCount - getBombsCounter() == openedFieldsCount) {
            state = GameState.GAME_OVER;
            notifyObservers(new Event(EventType.USER_WIN, this));
            System.out.println("FINISHING " + timer.getElapsed());
            future.cancel(true);
            executor.shutdownNow();
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

    public void updateFlag(Dot clickedDot) {
        var el = getElementByCoords(clickedDot);
        if (availableFlagsCounter <= 0 && !el.isFlagged()) {
            return;
        }


        if (el.isFlagged()) {
            availableFlagsCounter++;
        } else {
            availableFlagsCounter--;
        }

        el.updateFlagged();
        notifyObservers(new Event(EventType.REDRAW_REQUEST, this));
    }


    public enum GameState {
        RUNNING,
        GAME_OVER,
    }

}


