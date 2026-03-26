package model;

public class BorrowRecord {
    private int recordId;
    private int studentId;
    private int bookId;
    private String borrowDate;
    private String returnDate;  // null means not yet returned

    public BorrowRecord(int recordId, int studentId, int bookId,
                        String borrowDate, String returnDate) {
        this.recordId   = recordId;
        this.studentId  = studentId;
        this.bookId     = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getRecordId()                      { return recordId; }
    public void setRecordId(int recordId)         { this.recordId = recordId; }

    public int getStudentId()                     { return studentId; }
    public void setStudentId(int studentId)       { this.studentId = studentId; }

    public int getBookId()                        { return bookId; }
    public void setBookId(int bookId)             { this.bookId = bookId; }

    public String getBorrowDate()                 { return borrowDate; }
    public void setBorrowDate(String borrowDate)  { this.borrowDate = borrowDate; }

    public String getReturnDate()                 { return returnDate; }
    public void setReturnDate(String returnDate)  { this.returnDate = returnDate; }
}
