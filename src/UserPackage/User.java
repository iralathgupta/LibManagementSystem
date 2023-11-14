package UserPackage;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import BooksPackage.*;

public class User {
    private String username;
    private int uid;
    private Book[] borrowedBooks;
    private Date[] borrowedDates;
    private int borrowedCount;

    public User(String username, int uid) {
        this.username = username;
        this.uid = uid;
        this.borrowedBooks = new Book[10];
        this.borrowedDates = new Date[10];
        this.borrowedCount = 0;
    }
   

    public String getUsername() {
        return username;
    }

    public Book[] getBorrowedBooks() {
        return borrowedBooks;
    }

    public Date[] getBorrowedDates() {
        return borrowedDates;
    }

    public int getUid()
    {
        return uid;
    }

    public void addBorrowedBook(Book book, Date borrowedDate) {
        if (borrowedCount < borrowedBooks.length) {
            borrowedBooks[borrowedCount] = book;
            borrowedDates[borrowedCount] = borrowedDate;
            borrowedCount++;
        }
    }

    // public void displayBorrowedBooks() {
    //     System.out.println("Books borrowed by " + username + ":");
    //     for (int i = 0; i < borrowedCount; i++) {
    //         borrowedBooks[i].display();
    //     }
    // }

    public Date getBorrowedDateByISBN(String isbn) {
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i] != null && borrowedBooks[i].getIsbn().equals(isbn)) {
                return borrowedDates[i];
            }
        }
        return null;
    }

    public void returnBorrowedBook(Book book, Date borrowedDate, LocalDate returnDate) throws FineException, DaysException, NoDayException {
        if (returnDate == null) throw new NoDayException();
        int fineDays = calculateFineDays(book, borrowedDate, returnDate);
   

        
        if (calculateDaysDiff(book, borrowedDate, returnDate) < 0)
            throw new DaysException();
        if (fineDays > 0) {
            throw new FineException(fineDays * 10); // Throw FineException with calculated fine
        }
    
        book.returnBook();
    
        // Compact the arrays after returning the book
        int indexToRemove = -1;
        for (int i = 0; i < borrowedCount; i++) {
            if (borrowedBooks[i] != null && borrowedBooks[i].getIsbn().equals(book.getIsbn())) {
                indexToRemove = i;
                break;
            }
        }
    
        if (indexToRemove >= 0) {
            // Remove the book and date at the found index
            for (int i = indexToRemove; i < borrowedCount - 1; i++) {
                borrowedBooks[i] = borrowedBooks[i + 1];
                borrowedDates[i] = borrowedDates[i + 1];
            }
    
            // Set the last entry to null
            borrowedBooks[borrowedCount - 1] = null;
            borrowedDates[borrowedCount - 1] = null;
    
            // Update the borrowed count
            borrowedCount--;
        }
    }


    private int calculateFineDays(Book book, Date borrowedDate, LocalDate returnDate){
       LocalDate borrowedLocalDate = borrowedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        

        // Calculate the difference in days between borrowed date and return date
        long daysDifference = Duration.between(borrowedLocalDate.atStartOfDay(), returnDate.atStartOfDay()).toDays();
        // Fine calculation (for example, 10 rupees for each day overdue)
        long fine = Math.max(0, daysDifference - 7) * 10;
    
        // Convert to int (handle potential overflow)
        int fineInt = (int) Math.min(Integer.MAX_VALUE, fine);
    
        return fineInt;
    }

    private long calculateDaysDiff(Book book, Date borrowedDate, LocalDate returnDate){
        LocalDate borrowedLocalDate = borrowedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    
        // Calculate the difference in days between borrowed date and return date
        long daysDifference = Duration.between(borrowedLocalDate.atStartOfDay(), returnDate.atStartOfDay()).toDays();
        return daysDifference;
    }
    
}

