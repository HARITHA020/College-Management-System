package dao;

import java.util.ArrayList;
import java.util.List;
import model.BorrowRecord;

public class BorrowRecordDAO {

    private static List<BorrowRecord> records = new ArrayList<>();
    private static int recordCounter = 1;

    // Borrow Book
    public void borrowBook(BorrowRecord record) {
        record.setRecordId(recordCounter++); 
        records.add(record);
    }

    //Get All Records
    public List<BorrowRecord> getAllRecords() {
        return records;
    }

    // Get Record by ID
    public BorrowRecord getRecordById(int id) {
        for (BorrowRecord r : records) {
            if (r.getRecordId() == id) {
                return r;
            }
        }
        return null;
    }
}