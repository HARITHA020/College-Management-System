package model;

public class BorrowRecord {
    private int recordId;
    private Integer studentId; // nullable for faculty
    private Integer facultyId; // nullable for student
    private int bookId;
    private String borrowDate;
    private String returnDate;  // null means not yet returned

    public BorrowRecord(int recordId, Integer studentId, Integer facultyId, int bookId,
                        String borrowDate, String returnDate) {
        this.recordId   = recordId;
        this.studentId  = studentId;
        this.facultyId  = facultyId;
        this.bookId     = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getFacultyId() { return facultyId; }
    public void setFacultyId(Integer facultyId) { this.facultyId = facultyId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }
}