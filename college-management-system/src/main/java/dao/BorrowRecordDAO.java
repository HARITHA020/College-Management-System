package dao;

import java.util.ArrayList;
import java.util.List;
import model.BorrowRecord;

public class BorrowRecordDAO {

    private static List<BorrowRecord> records = new ArrayList<>();

    public void borrowBook(BorrowRecord record) {
        records.add(record);
    }

    public List<BorrowRecord> getAllRecords() {
        return records;
    }
}