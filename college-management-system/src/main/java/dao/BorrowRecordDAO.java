/*
 * Borrow Record DAO
 * Author: Jerishwin Joseph
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Book;
import model.BorrowRecord;

public class BorrowRecordDAO {

	// 🔹 BORROW BOOK
	public void borrowBook(BorrowRecord record) {
	    try {
	        Connection con = DBConnection.getConnection();

	        // Insert borrow record
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
	            // Debug: show available copies before
	            BookDAO bookDAO = new BookDAO();
	            Book bookBefore = bookDAO.getBookById(record.getBookId());
	            System.out.println("Available copies before borrow: " + bookBefore.getAvailableCopies());

	            // Update availability
	            updateBookAvailability(record.getBookId(), false);

	            Book bookAfter = bookDAO.getBookById(record.getBookId());
	            System.out.println("Available copies after borrow: " + bookAfter.getAvailableCopies());

	            System.out.println("✅ Book borrowed successfully!");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // 🔹 RETURN BOOK
    public void returnBook(int recordId, String returnDate) {
        try {
            Connection con = DBConnection.getConnection();

            BorrowRecord record = getRecordById(recordId);
            if (record == null) return; // safety

            // Only update if not already returned
            if (record.getReturnDate() == null) {
                String query = "UPDATE borrow_records SET return_date=? WHERE record_id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, returnDate);
                ps.setInt(2, recordId);
                ps.executeUpdate();

                // Increase available copies safely
                updateBookAvailability(record.getBookId(), true);

                System.out.println("✅ Book returned successfully");
            } else {
                System.out.println("⚠ Book already returned.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE BOOK AVAILABILITY
    private void updateBookAvailability(int bookId, boolean isReturn) {
        try {
            Connection con = DBConnection.getConnection();
            String query;
            if (isReturn) {
                // Increase but never exceed total_copies
                query = "UPDATE books SET available_copies = LEAST(available_copies + 1, total_copies) WHERE book_id=?";
            } else {
                // Decrease but never below 0
                query = "UPDATE books SET available_copies = GREATEST(available_copies - 1, 0) WHERE book_id=?";
            }
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get Record By ID
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
    
    // Delete Records by Student ID 
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

    // Get Active Borrow Records 
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