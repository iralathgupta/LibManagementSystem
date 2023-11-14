
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import UserPackage.*;
import BooksPackage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import UserPackage.UserDatabase;

public class LoginSceneController implements Initializable{

    @FXML
    private Button LoginBtn;
    @FXML
    private TextField loginCred;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

     @FXML
    public void goToHome(ActionEvent event) {
        // Load the FXML file for MainScene
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
            Scene LoginPageScene = new Scene(loader);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            
            appStage.setScene(LoginPageScene);
            appStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    @FXML
    public void closeLogin(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void LoginFunc(ActionEvent event) {
         String userInput = loginCred.getText(); // Retrieve the text from the TextField
         System.out.println("User input: " + userInput);
         int regid;
         User user = null;

         
    
        
            try{
                regid = Integer.parseInt(userInput);
                 user = credentials(regid, UserDatabase.users);
            }catch(NumberFormatException e){
                user = credentials(userInput,UserDatabase.users);
        }// Print the input (you can handle it as needed)

        try {
            
            if(user!=null && !userInput.isEmpty())
            {
                AppData.getInstance().setCurrentUser(user);
            
            Parent loader = FXMLLoader.load(getClass().getResource("HomeScene.fxml"));
            Scene LoginPageScene = new Scene(loader);
            Stage appStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            loader.setOnMousePressed(mouseEvent -> {
                appStage.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()});
            });
    
            loader.setOnMouseDragged(mouseEvent -> {
                double[] data = (double[]) appStage.getUserData();
                appStage.setX(mouseEvent.getScreenX() - data[0]);
                appStage.setY(mouseEvent.getScreenY() - data[1]);
            });
            
            appStage.setScene(LoginPageScene);
            appStage.show();
        }

        else
        {
            System.out.println("No User Entered.");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText(null);
            alert.setContentText("Enter Valid Username or UID");
            alert.show();


        }

 
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
        
    }

    public static <T> User credentials(T id, User[] users) {
        int in = -1;
        if (id instanceof Integer) {
            int regid = (Integer) id;
            for (int i = 0; i < users.length; i++) {
                if (regid == users[i].getUid()) {
                    in = i;
                    break;
                }
            }
        } else if (id instanceof String) {
            String name = (String) id;
    
            for (int i = 0; i < users.length; i++) {
                if (users[i].getUsername().toLowerCase().contains(name.toLowerCase())) {
                    in = i;
                    break;
                }
            }
        } else {
            System.out.println("Invalid input type");
            return null;
        }
    
        if (in != -1) {
            System.out.println("Welcome back, " + users[in].getUsername() + "!");
            return users[in];
        } else {
            return null;
        }
    }
   
    
    
    
    
    
    
}

