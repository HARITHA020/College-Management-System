package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    // 🔍 Search Book
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

    // 📚 Borrow Book
    public void borrowBook(int userId, String userType, int bookId) {
        if (userId <= 0 || bookId <= 0) {
            System.out.println("Invalid ID");
            return;
        }

        Book book = bookDAO.getBookById(bookId);
        if (book == null) { System.out.println("Book not found"); return; }
        if (!book.isAvailable()) { System.out.println("Book already borrowed"); return; }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Integer studentId = null, facultyId = null;
        if (userType.equalsIgnoreCase("student")) studentId = userId;
        else if (userType.equalsIgnoreCase("faculty")) facultyId = userId;
        else { System.out.println("Invalid user type"); return; }

        BorrowRecord record = new BorrowRecord(0, studentId, facultyId, bookId, today, null);
        borrowRecordDAO.borrowBook(record);
        book.setAvailable(false);
        System.out.println(userType + " borrowed book successfully");
    }

    // 🔁 Return Book
    public void returnBook(int userId, String userType, int bookId) {
        // ✅ Get all active borrows
        List<BorrowRecord> activeRecords = borrowRecordDAO.getActiveRecords();

        // Find the active record for this user & book
        BorrowRecord record = null;
        for (BorrowRecord r : activeRecords) {
            if (r.getBookId() == bookId) {
                if (userType.equalsIgnoreCase("student") && r.getStudentId() != null && r.getStudentId() == userId) {
                    record = r; break;
                } else if (userType.equalsIgnoreCase("faculty") && r.getFacultyId() != null && r.getFacultyId() == userId) {
                    record = r; break;
                }
            }
        }

        if (record == null) {
            System.out.println("No active borrow record found for this user and book");
            return;
        }

        // ✅ Set return date
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        borrowRecordDAO.returnBook(record.getRecordId(), today);

        // ✅ Update book availability
        Book book = bookDAO.getBookById(bookId);
        if (book != null) book.setAvailable(true);

        System.out.println(userType + " returned book successfully");
    }
}