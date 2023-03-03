package fit.nsu.labs.views;

import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

public interface Viewer {
    void showGameTable(GameField field, int columnSize, int rowSize);

    Dot clickButton();
}
