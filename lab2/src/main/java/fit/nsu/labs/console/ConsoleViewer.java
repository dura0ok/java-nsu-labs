package fit.nsu.labs.console;

import fit.nsu.labs.model.*;

public class ConsoleViewer implements Observer {

    private void printField(GameField model, boolean gameFinished) {
        for (int i = 0; i < model.getColumnSize(); i++) {
            for (int j = 0; j < model.getRowSize(); j++) {
                var el = model.getElementByCoords(new Dot(i, j));
                if (gameFinished) {
                    System.out.print(el.getBombsAroundCount());
                } else {
                    if (!el.isOpened()) {
                        //System.out.print("*(" + el.getBombsAroundCount() + ", " + (el.isBomb() ? "Bomb" : "simp") + ")");
                        System.out.print("*");
                    } else {
                        System.out.print(el.getBombsAroundCount());
                    }
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    @Override
    public void notification(Event event) {
        if (event.type() == EventType.REDRAW_REQUEST) {
            printField(event.field(), false);
            return;
        }

        if (event.type() == EventType.BOMB_OPENED) {
            System.out.println("You opened bomb. Game failed");
            printField(event.field(), true);
        }

        if (event.type() == EventType.ALREADY_CLICKED) {
            System.out.println("Already clicked");
        }

        if (event.type() == EventType.USER_WIN) {
            System.out.println("You win!!!");
        }
    }
}
