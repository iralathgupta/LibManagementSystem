package BooksPackage;
import UserPackage.*;
import java.util.Date;

interface BookData{
    void returnBook();
    String getIsbn();
    String getName();
    String getAuthor();
    String getGenre();
    int getInventory();
}

public class Book implements BookData{
    public String name;
    public String author;
    public String genre;
    public String isbn;
    public int inventory;
    private Date borrowedDate;

    Book(String name, String author, String genre, String isbn, int inv) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        inventory = inv;
        borrowedDate = null;
    }

    // synchronized void display() {
    //     SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    //     System.out.println("Book name: " + name);
    //     System.out.println("Book author: " + author);
    //     System.out.println("Book genre: " + genre);
    //     System.out.println("Book isbn code: " + isbn);
    //     System.out.println("No. of books left: " + inventory);
    //     if (borrowedDate != null) {
    //         System.out.println("Borrowed on: " + dateFormat.format(borrowedDate));
    //     }
    //     System.out.println();
    // }

    // public synchronized boolean borrowBook(User user) {
    //     if (inventory > 0) {
    //         inventory--;
    //         borrowedDate = new Date();
    //         user.addBorrowedBook(this, borrowedDate);
    //         return true;
    //     }
    //     return false;
    // }

    public void returnBook() {
        inventory++;
        borrowedDate = null;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getInventory() {
        return inventory;
    }
}