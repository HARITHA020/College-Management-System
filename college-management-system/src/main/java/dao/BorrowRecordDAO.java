package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.BorrowRecord;

public class BorrowRecordDAO {

    // 🔹 BORROW BOOK
    public void borrowBook(BorrowRecord record) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO borrow_records(student_id, faculty_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?, NULL)";

            PreparedStatement ps = con.prepareStatement(query);
            if (record.getStudentId() != null)
                ps.setInt(1, record.getStudentId());
            else
                ps.setNull(1, Types.INTEGER);

            if (record.getFacultyId() != null)
                ps.setInt(2, record.getFacultyId());
            else
                ps.setNull(2, Types.INTEGER);

            ps.setInt(3, record.getBookId());
            ps.setString(4, record.getBorrowDate());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                updateBookAvailability(record.getBookId(), false);
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

            int bookId = getBookIdByRecordId(recordId);

            String query = "UPDATE borrow_records SET return_date=? WHERE record_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, returnDate);
            ps.setInt(2, recordId);
            ps.executeUpdate();

            if (bookId != -1) {
                updateBookAvailability(bookId, true);
            }

            System.out.println("✅ Book returned successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        rs.getObject("student_id") != null ? rs.getInt("student_id") : null,
                        rs.getObject("faculty_id") != null ? rs.getInt("faculty_id") : null,
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

    // 🔹 UPDATE BOOK AVAILABILITY
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

    private int getBookIdByRecordId(int recordId) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT book_id FROM borrow_records WHERE record_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, recordId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("book_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    //deletion
    public void deleteByStudentId(int studentId) {

        String query = "DELETE FROM borrow_records WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, studentId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ACTIVE BORROWS
    public List<BorrowRecord> getActiveRecords() {
        List<BorrowRecord> records = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM borrow_records WHERE return_date IS NULL";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                records.add(new BorrowRecord(
                        rs.getInt("record_id"),
                        rs.getObject("student_id") != null ? rs.getInt("student_id") : null,
                        rs.getObject("faculty_id") != null ? rs.getInt("faculty_id") : null,
                        rs.getInt("book_id"),
                        rs.getString("borrow_date"),
                        rs.getString("return_date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}