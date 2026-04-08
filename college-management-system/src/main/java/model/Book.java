/*
 * Book Model
 * Author: Jerishwin Joseph
 */
package model;

//Book model class representing a book in the library
public class Book {
    private int bookId;
    private String title;
    private String author;
    private String isbn;
    private int totalCopies;
    private int availableCopies;

	public Book(int bookId, String title, String author, String isbn, int totalCopies, int availableCopies) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.totalCopies = totalCopies;
		this.availableCopies = availableCopies;
	}
    public int getBookId()                      { return bookId; }
    public void setBookId(int bookId)           { this.bookId = bookId; }

    public String getTitle()                    { return title; }
    public void setTitle(String title)          { this.title = title; }

    public String getAuthor()                   { return author; }
    public void setAuthor(String author)        { this.author = author; }

    public String getIsbn()                     { return isbn; }
    public void setIsbn(String isbn)            { this.isbn = isbn; }

    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    
    @Override
    public String toString() {
        return String.format("%-10d %-20s %-20s %-15s %-10s",
                bookId,
                title,
                author,
                isbn);
    }
    
}