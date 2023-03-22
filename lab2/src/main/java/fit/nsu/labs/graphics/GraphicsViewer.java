package fit.nsu.labs.graphics;

import fit.nsu.labs.model.*;

import javax.swing.*;



public class GraphicsViewer implements Observer {

    private void printField(GameField model, boolean gameFinished) {
        for (int i = 0; i < model.getColumnSize(); i++) {
            for (int j = 0; j < model.getRowSize(); j++) {
                var el = model.getElementByCoords(new Dot(i, j));

            }
        }
    }
    @Override
    public void notification(Event event) {
        if (event.type() == EventType.REDRAW_REQUEST) {
            printField(event.field(), false);
            return;
        }
    }
}


class FieldElement extends JButton {
    private final Dot dot;

    public FieldElement(String text, Dot dot) {
        super(text);
        this.dot = dot;
    }

    public Dot getDot() {
        return dot;
    }

}