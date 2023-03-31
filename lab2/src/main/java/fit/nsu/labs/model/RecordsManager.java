package fit.nsu.labs.model;

import fit.nsu.labs.exceptions.MineSweeperGameException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordsManager {

    private final Map<GameLevels, String> record_files = new HashMap<>() {{
        put(GameLevels.EASY, "easy.txt");
        put(GameLevels.MEDIUM, "medium.txt");
        put(GameLevels.HARD, "hard.txt");
        put(GameLevels.CUSTOM, "custom.txt");
    }};

    public List<Record> readRecords(GameLevels level) throws MineSweeperGameException {
        List<Record> records = new ArrayList<>();
        try (var reader = new BufferedReader(new FileReader(record_files.get(level)))) {
            String line = reader.readLine();
            while (line != null) {
                var splitted = line.split("\\s+");
                if (splitted.length != 2) {
                    line = reader.readLine();
                    continue;
                }

                String currentName = splitted[0];
                long currentTime = Long.parseLong(splitted[1]);

                records.add(new Record(currentName, currentTime));
                line = reader.readLine();
            }
        } catch (FileNotFoundException ignored) {
            return records;
        } catch (Exception e) {
            throw new MineSweeperGameException("error in read records", e);
        }
        return records;
    }

    public void writeRecord(GameLevels level, Record record) {

        try {
            var data = readRecords(level);
            data.add(record);
            data.sort((o1, o2) -> (int) (o1.secondsTime() - o2.secondsTime()));


            if (data.size() > 10) {
                data.remove(10);
            }


            try (var writer = new BufferedWriter(new FileWriter(record_files.get(level), false))) {
                for (var item : data) {
                    writer.write(item.toString());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
