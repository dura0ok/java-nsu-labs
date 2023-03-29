package fit.nsu.labs.model;

import fit.nsu.labs.exceptions.MineSweeperGameException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecordsManager {
    private final Map<GameLevels, String> record_files = new HashMap<>() {{
        put(GameLevels.EASY, "easy.txt");
        put(GameLevels.MEDIUM, "medium.txt");
        put(GameLevels.HARD, "hard.txt");
        put(GameLevels.CUSTOM, "custom.txt");
    }};

    public void writeRecord(GameLevels level, Record record) {
        try (var writer = new BufferedWriter(new FileWriter(record_files.get(level)))) {
            writer.write(record.toString());
        } catch (IOException e) {
            throw new MineSweeperGameException("record file not found", e);
        }

    }
}
