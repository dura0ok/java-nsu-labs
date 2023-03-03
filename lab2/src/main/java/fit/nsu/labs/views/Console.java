package fit.nsu.labs.views;

import fit.nsu.labs.model.Dot;
import fit.nsu.labs.model.GameField;

public class Console implements Viewer {
    public void showGameTable(GameField field, int columnSize, int rowSize) {
        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                var el = field.getElementByCoords(new Dot(i, j));
                if (!el.isOpened()) {
                    //System.out.print("*(" + el.getBombsAroundCount() + ", " + (el.isBomb() ? "Bomb" : "simp") + ")");
                    System.out.print("*");
                } else {
                    System.out.print(el.getBombsAroundCount());
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void clickButton(Dot dot) {

    }
}
