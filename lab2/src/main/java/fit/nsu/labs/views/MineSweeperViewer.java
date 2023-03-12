package fit.nsu.labs.views;

import fit.nsu.labs.model.GameField;

public abstract class MineSweeperViewer implements Viewer {
    protected final GameField field;
    protected final int columnSize;
    protected final int rowSize;


    MineSweeperViewer(GameField field, int columnSize, int rowSize) {
        this.field = field;
        this.columnSize = columnSize;
        this.rowSize = rowSize;
    }

}
