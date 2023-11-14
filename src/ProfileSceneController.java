
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import BooksPackage.Book;
import javafx.fxml.Initializable;
import java.util.Date;

import UserPackage.*;
import BooksPackage.*;


public class ProfileSceneController implements Initializable {

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, Integer> isbnColumn;

    @FXML
    private TextField isbnTextField;

    @FXML
    private Button ProfiletoLoginBtn;

    @FXML
    private Button ProfileToHomeBtn;

    @FXML
    private Button ReturnBookBtn;

    @FXML
    private TextField returnISBNTextField;

    @FXML
    private DatePicker datePicker;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (bookTableView != null) {
            //System.out.println("HELP ME OMG");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    
            User currentUser = AppData.getInstance().getCurrentUser();
            if (currentUser != null) {
                Book[] userBorrowedBooks = currentUser.getBorrowedBooks();
                if (userBorrowedBooks != null) {
                    ObservableList<Book> borrowedBooks = FXCollections.observableArrayList(userBorrowedBooks);
                    bookTableView.setItems(borrowedBooks);
                } else {
                    bookTableView.setItems(FXCollections.emptyObservableList());
                }
            }
        }
    }
    

    @FXML
    public void goToLogin(ActionEvent event) {
        // Define actions when the "Back To Login" button is clicked
        try {
            AppData.getInstance().setCurrentUser(null);
            Parent loader = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
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
    public void backToHome(ActionEvent event) {
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
    public void returnBook() {
        String isbn = returnISBNTextField.getText();
    
        User currentUser = AppData.getInstance().getCurrentUser();
        if (currentUser != null) {
            Book bookToReturn = null;
    
            // Find the borrowed book in the user's list
            for (Book borrowedBook : currentUser.getBorrowedBooks()) {
                if (borrowedBook != null && borrowedBook.getIsbn().equals(isbn)) {
                    bookToReturn = borrowedBook;
                    break;
                }
            }
    
            if (bookToReturn != null) {
                // Get the borrowed date
                Date borrowedDate = currentUser.getBorrowedDateByISBN(isbn);
                
                // Get the return date from the DatePicker
                //LocalDate returnDate = datePicker.getValue(); // Use the datePicker from FXML
               
                // Return the book and handle fines
                try {
                    LocalDate returnDate = datePicker.getValue(); 
                    currentUser.returnBorrowedBook(bookToReturn, borrowedDate, returnDate);
    
                    // Update the TableView directly
    
                    // Show a success message
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Book Return");
                    alert.setHeaderText(null);
                    alert.setContentText("Book Successfully Returned");
                    alert.show();
                } catch (FineException e) {
                    // Show an alert with fine information
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Book Return");
                    alert.setHeaderText(null);
                    alert.setContentText("Delayed Book Return. \n" + e.toString());
                    alert.show();
                } catch (DaysException e){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Book Return");
                    alert.setHeaderText(null);
                    alert.setContentText(e.toString());     
                    alert.show();       
                } catch (NoDayException e){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Book Return");
                    alert.setHeaderText(null);
                    alert.setContentText(e.toString());     
                    alert.show();   
                }
            } else {
                // Show an error message if the book is not found
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Book with ISBN " + isbn + " not found in the user's borrowed books.");
                alert.show();
            }

            
        }
        ObservableList<Book> borrowedBooks = FXCollections.observableArrayList(currentUser.getBorrowedBooks());
bookTableView.setItems(borrowedBooks);
    }

    
    

}

