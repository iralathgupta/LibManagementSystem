
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import UserPackage.*;
import BooksPackage.*;

public class HomeSceneController {

    @FXML
    private Text welcomeText;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Text searchLabelText;

    @FXML
    private Button HomeToLoginBtn;

    @FXML
    private Button userProfilebtn;

    

    @FXML
    public void initialize() {


            User currentUser = AppData.getInstance().getCurrentUser();
            if (currentUser != null) {
                welcomeText.setText("Welcome Back " + currentUser.getUsername());
            }
        
    }

    // Define action/event handling methods here
    @FXML
    public void searchAction(ActionEvent event) {
        // Implement what happens when the search button is clicked
        String searchText = searchField.getText();
        // Add your logic here for handling the search functionality
        System.out.println("Search button clicked with text: " + searchText);
        AppData.getInstance().setSearchText(searchText);
        
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("SearchScene.fxml"));
            Scene HomePageScene = new Scene(loader);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            loader.setOnMousePressed(mouseEvent -> {
                appStage.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()});
            });
    
            loader.setOnMouseDragged(mouseEvent -> {
                double[] data = (double[]) appStage.getUserData();
                appStage.setX(mouseEvent.getScreenX() - data[0]);
                appStage.setY(mouseEvent.getScreenY() - data[1]);
            });
            
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    private void goToProfile(ActionEvent event) {
        // Add the functionality to perform when userProfilebtn is clicked
        System.out.println("View Library Profile button clicked.");
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("ProfileScene.fxml"));
            Scene HomePageScene = new Scene(loader);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            loader.setOnMousePressed(mouseEvent -> {
                appStage.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()});
            });
    
            loader.setOnMouseDragged(mouseEvent -> {
                double[] data = (double[]) appStage.getUserData();
                appStage.setX(mouseEvent.getScreenX() - data[0]);
                appStage.setY(mouseEvent.getScreenY() - data[1]);
            });
            
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    

    @FXML
    private void goToLogin(ActionEvent event) {
        
        try {
            AppData.getInstance().setCurrentUser(null);
            Parent loader = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            Scene HomePageScene = new Scene(loader);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            loader.setOnMousePressed(mouseEvent -> {
                appStage.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()});
            });
    
            loader.setOnMouseDragged(mouseEvent -> {
                double[] data = (double[]) appStage.getUserData();
                appStage.setX(mouseEvent.getScreenX() - data[0]);
                appStage.setY(mouseEvent.getScreenY() - data[1]);
            });
            
            appStage.setScene(HomePageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
}

