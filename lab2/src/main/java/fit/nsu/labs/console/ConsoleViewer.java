package fit.nsu.labs.console;

import fit.nsu.labs.model.*;

public class ConsoleViewer implements onEvent {

    public static void main(String[] args) {
        var game = new ConsoleController(new ConsoleViewer());
        game.startGame();

    }

    private void printField(GameField model) {
        for (int i = 0; i < model.getColumnSize(); i++) {
            for (int j = 0; j < model.getRowSize(); j++) {
                var el = model.getElementByCoords(new Dot(i, j));
                var state = model.getState();
                if (state.equals(GameField.GameState.GAME_OVER) || el.isOpened()) {
                    System.out.print(el.getBombsAroundCount());
                } else {
                    if (el.isFlagged()) {
                        System.out.print("F ");
                        continue;
                    }
                    System.out.print("*");
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
            printField(event.field());
            return;
        }

        if (event.type() == EventType.BOMB_OPENED) {
            System.out.println("You opened bomb. Game failed");
            printField(event.field());
        }

        if (event.type() == EventType.ALREADY_CLICKED) {
            System.out.println("Already clicked");
        }

        if (event.type() == EventType.USER_WIN) {
            System.out.println("You win!!!");
        }

        if (event.type().equals(EventType.FLAG_STATE_UPDATE)) {
            printField(event.field());
            return;
        }


        if (event.type().equals(EventType.ALREADY_FLAGGED)) {
            System.out.println("this coordinates already flagged");
        }

        if (event.type().equals(EventType.RECORDS_ERROR)) {
            System.err.println("Error in writing records");
        }


    }
}
