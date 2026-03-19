package service;

import java.util.List;
import java.util.Date;

import dao.BookDAO;
import dao.BorrowRecordDAO;

import model.Book;
import model.BorrowRecord;

public class LibraryService {

    private BookDAO bookDAO;
    private BorrowRecordDAO borrowRecordDAO;

    // ✅ Constructor
    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
    }

    // 🔍 Search Book (optional but useful)
    public void searchBook(String keyword) {

        List<Book> books = bookDAO.getAllBooks();
        boolean found = false;

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found");
        }
    }

    // 📚 Borrow Book (YOUR FIXED METHOD)
    public void borrowBook(int userId, String userType, int bookId) {

        if (userId <= 0) {
            System.out.println("Invalid User ID");
            return;
        }

        if (bookId <= 0) {
            System.out.println("Invalid Book ID");
            return;
        }

        Book book = bookDAO.getBookById(bookId);

        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        if (!book.isAvailability()) {
            System.out.println("Book already borrowed");
            return;
        }

        book.setAvailability(false);

        int studentId = 0;
        int facultyId = 0;

        if (userType.equalsIgnoreCase("student")) {
            studentId = userId;
        } 
        else if (userType.equalsIgnoreCase("faculty")) {
            facultyId = userId;
        } 
        else if (userType.equalsIgnoreCase("admin")) {
            facultyId = userId; // treat admin like faculty
        } 
        else {
            System.out.println("Invalid user type");
            return;
        }

        BorrowRecord record = new BorrowRecord(studentId, facultyId, bookId);

        borrowRecordDAO.borrowBook(record);

        System.out.println(userType + " borrowed book successfully");
    }

    // 🔁 Return Book
    public void returnBook(int recordId) {

        BorrowRecord record = borrowRecordDAO.getRecordById(recordId);

        if (record == null) {
            System.out.println("Record not found");
            return;
        }

        if (record.getReturnDate() != null) {
            System.out.println("Book already returned");
            return;
        }

        record.setReturnDate(new Date());

        Book book = bookDAO.getBookById(record.getBookId());
        if (book != null) {
            book.setAvailability(true);
        }

        System.out.println("Book returned successfully");
    }
}