package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Book;

public class BookDAO {

    // 🔹 ADD BOOK
    public void addBook(String title, String author, String isbn) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO books(title, author, isbn, available) VALUES (?, ?, ?, TRUE)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, isbn);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Book added successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE BOOK
    public void updateBook(int bookId, String title, String author, String isbn) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE books SET title=?, author=?, isbn=? WHERE book_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, isbn);
            ps.setInt(4, bookId);

            ps.executeUpdate();

            System.out.println("✅ Book updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 REMOVE BOOK
    public void removeBook(int bookId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE books SET available = false WHERE book_id=?";
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

    // 🔹 GET ALL BOOKS
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
                        rs.getBoolean("available")
                );

                books.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // 🔹 GET BOOK BY ID
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
                        rs.getBoolean("available")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔹 SEARCH BOOKS BY KEYWORD (title or author)
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
                        rs.getBoolean("available")
                );

                books.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // 🔹 UPDATE BOOK AVAILABILITY
    public void updateAvailability(int bookId, boolean available) {
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