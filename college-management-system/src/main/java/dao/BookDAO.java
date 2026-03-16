package dao;

import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookDAO {

    private static List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(int id) {
        books.removeIf(b -> b.getBookId() == id);
    }

    public List<Book> getAllBooks() {
        return books;
    }
}