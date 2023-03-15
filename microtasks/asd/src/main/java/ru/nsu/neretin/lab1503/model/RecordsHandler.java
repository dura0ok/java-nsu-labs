package ru.nsu.neretin.lab1503.model;

import java.io.*;
import java.util.ArrayList;

public class RecordsHandler {
    ArrayList<Record> records;
    private final BufferedWriter writer;
    private final String recordsFileName  = "/home/durachok/workspace/java-nsu-labs/microtasks/task5/src/main/resources/records.data";

    public RecordsHandler() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(recordsFileName));
    }

    public void pushRecord(String name, int points) throws IOException {
        records.add(new Record(name, points));
        updateRecordsFile();
    }

    private void updateRecordsFile() throws IOException {
        for(var record: records){
            writer.write(String.format("%s %d", record.getName(), record.getPoints()));
        }
    }

    public ArrayList<Record> getRecords() {
       return records;
    }

}

