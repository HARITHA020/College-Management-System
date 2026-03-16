package model;

import java.util.Date;

public class BorrowRecord {

    private int recordId;
    private int studentId;
    private int bookId;
    private Date borrowDate;
    private Date returnDate;

    public BorrowRecord() {}

    public BorrowRecord(int recordId, int studentId, int bookId,
                        Date borrowDate, Date returnDate) {
        this.recordId = recordId;
        this.studentId = studentId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
}