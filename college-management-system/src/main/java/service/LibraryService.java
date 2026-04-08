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

        System.out.println("\n------------------- SEARCH RESULTS -------------------");
        System.out.printf("%-10s %-20s %-20s %-15s %-10s\n",
                "ID", "Title", "Author", "ISBN", "Available");

        System.out.println("-------------------------------------------------------------------------------");
        for (Book b : books) {

            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
            	 String title = b.getTitle();
                 if (title.length() > 10) {
                     title = title.substring(0, 10) + "...";
                 }
            	System.out.printf("%-10d %-20s %-20s %-15s %-15s\n",
            	        b.getBookId(),
            	        title,
            	        b.getAuthor(),
            	        b.getIsbn(),
            	        b.getAvailableCopies() + "/" + b.getTotalCopies());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found");
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    
 // 📚 Borrow Book
    public void borrowBook(int userId, String userType, int bookId) {

        if (userId <= 0 || bookId <= 0) {
            System.out.println("Invalid ID");
            return;
        }

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        if (book.getAvailableCopies() <= 0) {
            System.out.println("No copies available");
            return;
        }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Integer studentId = null, facultyId = null;
        String borrowerType = "";

        if (userType.equalsIgnoreCase("student")) {
            studentId = userId;
            borrowerType = "Student";
        }
        else if (userType.equalsIgnoreCase("faculty")) {
            facultyId = userId;
            borrowerType = "Faculty";
        }
        else {
            System.out.println("Invalid user type");
            return;
        }

        BorrowRecord record = new BorrowRecord(0, studentId, facultyId, bookId, today, null);

        borrowRecordDAO.borrowBook(record);
        System.out.println("📌 " + borrowerType + " (ID: " + userId + ") borrowed '" + book.getTitle() + "'");
    }

    // 🔁 Return Book
    public void returnBook(int userId, String userType, int bookId) {

        List<BorrowRecord> activeRecords = borrowRecordDAO.getActiveRecords();

        BorrowRecord record = null;

        for (BorrowRecord r : activeRecords) {
            if (r.getBookId() == bookId) {

                if (userType.equalsIgnoreCase("student") &&
                    r.getStudentId() != null &&
                    r.getStudentId() == userId) {
                    record = r;
                    break;
                }

                else if (userType.equalsIgnoreCase("faculty") &&
                         r.getFacultyId() != null &&
                         r.getFacultyId() == userId) {
                    record = r;
                    break;
                }
            }
        }

        if (record == null) {
            System.out.println("No active borrow record found");
            return;
        }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        borrowRecordDAO.returnBook(record.getRecordId(), today);


        System.out.println(userType + " returned book successfully");
    }
}