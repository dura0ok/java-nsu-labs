package model;

import ru.nsu.neretin.lab1503.model.Record;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RecordsHandler {
    private final BufferedWriter writer;
    private final String recordsFileName = "/home/durachok/workspace/java-nsu-labs/microtasks/task5/out/records.data";
    ArrayList<ru.nsu.neretin.lab1503.model.Record> records = new ArrayList<>();

    public RecordsHandler() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(recordsFileName, true));
    }

    public void pushRecord(String name, int points) throws IOException {
        records.add(new Record(name, points));
        updateRecordsFile();
    }

    private void updateRecordsFile() throws IOException {
        for (ru.nsu.neretin.lab1503.model.Record record : records) {
            writer.write(String.format("%s %d", record.getName(), record.getPoints()));
        }
        writer.close();
    }

    public ArrayList<ru.nsu.neretin.lab1503.model.Record> getRecords() {
        return records;
    }

}

