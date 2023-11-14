package SearchPackage;
import BooksPackage.Book;

public class Search3 implements Runnable { // for genre
    int n;
    int count;
    String s;
    Book book[];
    public String[] isbnList;
    Thread t;

    public Search3(int n, Book book[], String s) {
        this.n = n;
        this.s = s.toLowerCase();
        count = 0;
        this.book = book;
        isbnList = new String[n];
        t = new Thread(this);
        t.start();
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            synchronized (Book.class) {
                String t1 = book[i].genre.toLowerCase();

                if (t1.contains(s)) {
                    //book[i].display();
                    isbnList[count] = book[i].getIsbn();
                    count++;
                }
            }
        }
    }
}

