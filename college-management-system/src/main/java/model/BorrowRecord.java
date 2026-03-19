package model;

import java.util.Date;

public class BorrowRecord {

    private int recordId;
    private int studentId;
    private int facultyId;
    private int bookId;
    private Date borrowDate;
    private Date returnDate;

    public BorrowRecord() {}

    // ❌ Old constructor (keep optional)
    public BorrowRecord(int recordId, int studentId, int facultyId, int bookId,
                        Date borrowDate, Date returnDate) {
        this.recordId = recordId;
        this.studentId = studentId;
        this.facultyId = facultyId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // ✅ NEW constructor (recommended)
    public BorrowRecord(int studentId, int facultyId, int bookId) {
        this.studentId = studentId;
        this.facultyId = facultyId;
        this.bookId = bookId;
        this.borrowDate = new Date(); // auto date
        this.returnDate = null;
    }

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    // 🔥 Add this for printing
    @Override
    public String toString() {
        return "Record ID: " + recordId +
               " | Student ID: " + studentId +
               " | Faculty ID: " + facultyId +
               " | Book ID: " + bookId +
               " | Borrow Date: " + borrowDate +
               " | Return Date: " + returnDate;
    }
}