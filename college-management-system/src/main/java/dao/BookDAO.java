/*
 * Book DAO
 * Author: Jerishwin Joseph
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Book;

public class BookDAO {


    // 🔹 ADD BOOK
	public void addBook(String title, String author, String isbn, int totalCopies,int available) {
	    try {
	        Connection con = DBConnection.getConnection();


	        String query = "INSERT INTO books(title, author, isbn, total_copies, available_copies) VALUES (?, ?, ?, ?, ?)";

	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, title);
	        ps.setString(2, author);
	        ps.setString(3, isbn);
	        ps.setInt(4, totalCopies);
	        ps.setInt(5, totalCopies); // initially all available

	        ps.executeUpdate();

	        System.out.println("✅ Book added successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // 🔹 UPDATE BOOK
  
    
    public boolean updateBookField(int bookId, String field, String value) {

        // Only allow valid fields
        if (!(field.equals("title") || field.equals("author") || field.equals("isbn") || field.equals("total_copies"))) {
            return false;
        }

        String sql = "UPDATE books SET " + field + " = ? WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Handle integer field
            if (field.equals("total_copies")) {
                ps.setInt(1, Integer.parseInt(value));
            } else {
                ps.setString(1, value);
            }

            ps.setInt(2, bookId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Remove Book
    public void removeBook(int bookId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "String query = \"UPDATE books SET available_copies = 0 WHERE book_id=?\";";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookId);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Book marked as unavailable");
            else
                System.out.println("Book not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    // Get All Books
    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM books";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                		rs.getInt("book_id"),
                	    rs.getString("title"),
                	    rs.getString("author"),
                	    rs.getString("isbn"),
                	    rs.getInt("total_copies"),
                	    rs.getInt("available_copies")
                );

                books.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // Get Book By ID
    public Book getBookById(int bookId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM books WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Book(
                		rs.getInt("book_id"),
                	    rs.getString("title"),
                	    rs.getString("author"),
                	    rs.getString("isbn"),
                	    rs.getInt("total_copies"),
                	    rs.getInt("available_copies")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Search Books by Title or Author
    public List<Book> searchBooks(String keyword) {

        List<Book> books = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book(
                		rs.getInt("book_id"),
                	    rs.getString("title"),
                	    rs.getString("author"),
                	    rs.getString("isbn"),
                	    rs.getInt("total_copies"),
                	    rs.getInt("available_copies")
                );

                books.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

   
}