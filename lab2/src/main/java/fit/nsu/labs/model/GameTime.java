package fit.nsu.labs.model;

public class GameTime implements Runnable {
    private final GameField field;
    private long secondsAlreadyRun;

    public GameTime(GameField field) {
        this.field = field;
    }

    public void reset() {
        secondsAlreadyRun = 0;
    }

    public void updateTimer() {
        secondsAlreadyRun++;
    }

    public long getElapsed() {
        return secondsAlreadyRun;
    }

    @Override
    public void run() {
        updateTimer();
        //System.out.printf("Elapsed: %d minutes %d seconds\n", elapsed / 60, elapsed);
        field.notifyObservers(new Event(EventType.REDRAW_TIMER, field));
    }
}