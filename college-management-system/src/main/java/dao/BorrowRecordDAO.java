package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.BorrowRecord;

public class BorrowRecordDAO {

    // 🔹 BORROW BOOK
    public void borrowBook(int studentId, int bookId, String borrowDate) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO borrow_records(student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, NULL)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, bookId);
            ps.setString(3, borrowDate);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                // mark book as unavailable
                updateBookAvailability(bookId, false);
                System.out.println("✅ Book borrowed successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 RETURN BOOK
    public void returnBook(int recordId, String returnDate) {
        try {
            Connection con = DBConnection.getConnection();

            // get book_id before updating
            int bookId = getBookIdByRecordId(recordId);

            String query = "UPDATE borrow_records SET return_date=? WHERE record_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, returnDate);
            ps.setInt(2, recordId);

            ps.executeUpdate();

            if (bookId != -1) {
                // mark book as available again
                updateBookAvailability(bookId, true);
            }

            System.out.println("✅ Book returned successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL BORROW RECORDS
    public List<BorrowRecord> getAllRecords() {

        List<BorrowRecord> records = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM borrow_records";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BorrowRecord r = new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getString("borrow_date"),
                        rs.getString("return_date")
                );

                records.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    // 🔹 GET RECORDS BY STUDENT ID
    public List<BorrowRecord> getRecordsByStudentId(int studentId) {

        List<BorrowRecord> records = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM borrow_records WHERE student_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BorrowRecord r = new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getString("borrow_date"),
                        rs.getString("return_date")
                );

                records.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    // 🔹 GET RECORD BY ID
    public BorrowRecord getRecordById(int recordId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM borrow_records WHERE record_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, recordId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getString("borrow_date"),
                        rs.getString("return_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔹 GET ACTIVE BORROWS (not yet returned)
    public List<BorrowRecord> getActiveRecords() {

        List<BorrowRecord> records = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM borrow_records WHERE return_date IS NULL";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BorrowRecord r = new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getString("borrow_date"),
                        rs.getString("return_date")
                );

                records.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    // ── Private Helpers ───────────────────────────────────

    // get book_id from a record (used during return)
    private int getBookIdByRecordId(int recordId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT book_id FROM borrow_records WHERE record_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, recordId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("book_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // toggle book availability in books table
    private void updateBookAvailability(int bookId, boolean available) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE books SET available=? WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setBoolean(1, available);
            ps.setInt(2, bookId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}