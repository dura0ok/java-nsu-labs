package fit.nsu.labs.graphics;

import fit.nsu.labs.model.GameLevels;
import fit.nsu.labs.model.Record;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RecordsTable extends AbstractTableModel {
    private final List<Record> records;
    private final GameLevels level;
    RecordsTable(List<Record> records, GameLevels level){
        this.records = records;
        this.level = level;
    }
    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Record record = records.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> record.name();
            case 1 -> record.secondsTime();
            case 2-> level;
            default -> throw new IndexOutOfBoundsException("Invalid column index");
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Name";
            case 1 -> "Time(seconds)";
            case 2 -> "Level";
            default -> throw new IndexOutOfBoundsException("Invalid column index");
        };
    }
}
