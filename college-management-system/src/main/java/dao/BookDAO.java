package dao;

import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookDAO {

    private static List<Book> books = new ArrayList<>();

    // ✅ Constructor to initialize default books
    public BookDAO() {
        if (books.isEmpty()) {
            books.add(new Book(1, "Java Basics", "James Gosling", "111", true));
            books.add(new Book(2, "OOP Concepts", "Bjarne Stroustrup", "222", true));
        }
    }

    // ➕ Add Book
    public void addBook(Book book) {
        books.add(book); // ✅ FIXED (use parameter)
    }

    // ❌ Remove Book
    public void removeBook(int id) {
        books.removeIf(b -> b.getBookId() == id);
    }

    // 📋 Get All Books
    public List<Book> getAllBooks() {
        return books;
    }

    // 🔍 Get Book by ID
    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.getBookId() == id) {
                return b;
            }
        }
        return null;
    }
}