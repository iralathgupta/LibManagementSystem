
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import BooksPackage.Book;
import BooksPackage.BookDatabase;
import SearchPackage.Search1;
import SearchPackage.Search2;
import SearchPackage.Search3;
import UserPackage.User;

import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class SearchSceneController {

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TextField BorrowTextField;

    @FXML
    private TableColumn<Book, String> name;
    @FXML
    private TableColumn<Book, String> author;
    @FXML
    private TableColumn<Book, String> isbn;
    @FXML
    private TableColumn<Book, String> genre;
    @FXML
    private TableColumn<Book, String> inventory;


    // Method triggered when Borrow button is clicked
    @FXML
    public void BorrowBook() {
        String isbn = BorrowTextField.getText();
        User currentUser = AppData.getInstance().getCurrentUser();
        Book bookToBorrow = null;
        for (Book book : BookDatabase.books) {
            if (book.getIsbn().equals(isbn)) {
                bookToBorrow = book;
                break;
            }
        }
        if (bookToBorrow != null && bookToBorrow.getInventory() > 0) {
            // Borrow the book if it exists and has available inventory
            
    
           if (currentUser != null) {
            Date borrowedDate = new Date(); // Current date as the borrowed date
            currentUser.addBorrowedBook(bookToBorrow, borrowedDate);
            System.out.println(borrowedDate);
            bookToBorrow.inventory--; // Reduce the book's inventory
            showAlert(AlertType.INFORMATION, "Book Borrowed", "Book with ISBN " + isbn + " has been borrowed by " + currentUser.getUsername());
        } else {
            showAlert(AlertType.ERROR, "User Not Logged In", "No user logged in to borrow the book.");
        }
    } else if (bookToBorrow == null) {
        showAlert(AlertType.ERROR, "Book Not Found", "Book with ISBN " + isbn + " not found in the database.");
    } else {
        showAlert(AlertType.WARNING, "Not Available", "The book with ISBN " + isbn + " is not available for borrowing.");
    }
        // Implement borrowing logic here using the entered ISBN code
        // System.out.println("Borrow button clicked with ISBN: " + isbn);
        // Perform borrowing actions here
    }

    // Method triggered when Back To Home button is clicked
    @FXML
    public void goToHome(ActionEvent event) {
        System.out.println("Back to Home button clicked");
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
            Scene profilePageScene = new Scene(loader);
            Stage appStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            loader.setOnMousePressed(mouseEvent -> {
                appStage.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()});
            });

            loader.setOnMouseDragged(mouseEvent -> {
                double[] data = (double[]) appStage.getUserData();
                appStage.setX(mouseEvent.getScreenX() - data[0]);
                appStage.setY(mouseEvent.getScreenY() - data[1]);
            });

            appStage.setScene(profilePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


    @FXML
    public void initialize() {
         String searchText = AppData.getInstance().getSearchText();
        
        ObservableList<Book> bookList = FXCollections.observableArrayList(BookDatabase.books);
        ObservableList<Book> searchedBooks = FXCollections.observableArrayList();

        Search1 searchByName = new Search1(bookList.size(), bookList.toArray(new Book[0]), searchText);
        Search2 searchByAuthor = new Search2(bookList.size(), bookList.toArray(new Book[0]), searchText);
        Search3 searchByGenre = new Search3(bookList.size(), bookList.toArray(new Book[0]), searchText);
        //System.out.println(bookList.size());
        Thread thread1 = new Thread(searchByName);
        Thread thread2 = new Thread(searchByAuthor);
        Thread thread3 = new Thread(searchByGenre);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Gather results from search threads
        String[] isbnList1 = searchByName.isbnList;
        String[] isbnList2 = searchByAuthor.isbnList;
        String[] isbnList3 = searchByGenre.isbnList;

        // Combine the ISBNs from all searches
        Set<String> combinedISBNs = new HashSet<>();
        combinedISBNs.addAll(Arrays.asList(isbnList1));
        combinedISBNs.addAll(Arrays.asList(isbnList2));
        combinedISBNs.addAll(Arrays.asList(isbnList3));

        // Fetch books based on the combined ISBNs
        for (String isbn : combinedISBNs) {
            for (Book book : bookList) {
                if (book.getIsbn().equals(isbn)) {
                    searchedBooks.add(book);
                }
            }
        }

        // Populate the TableView with the searched books
        if (searchedBooks.isEmpty()) {
            tableView.setPlaceholder(new Label("The item you have entered may not exist, try using different search parameters"));
        } else {
            // Populate the TableView with the searched books
            if (tableView != null && name != null) {
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                author.setCellValueFactory(new PropertyValueFactory<>("author"));
                genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
                isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
                inventory.setCellValueFactory(new PropertyValueFactory<>("inventory"));
                tableView.setItems(searchedBooks);
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.show();
}
}
