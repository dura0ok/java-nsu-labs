package fit.nsu.labs.model;

public class BoardElement {
    private final Dot boardCoords;
    private BoardElementType type;
    private boolean isOpened;
    private int bombsAroundCount;

    public BoardElement(Dot boardCoords) {
        this.type = BoardElementType.REGULAR_FIELD;
        this.boardCoords = boardCoords;
        isOpened = false;
    }

    public boolean isBomb() {
        return type == BoardElementType.BOMB;
    }

    public void makeBombType() {
        type = BoardElementType.BOMB;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public Dot getBoardCoords() {
        return boardCoords;
    }

    public int getBombsAroundCount() {
        return bombsAroundCount;
    }

    public void setBombsAroundCount(int bombsAroundCount) {
        this.bombsAroundCount = bombsAroundCount;
    }

    public void open() {
        isOpened = true;
    }

    enum BoardElementType {
        BOMB,
        REGULAR_FIELD
    }

}
