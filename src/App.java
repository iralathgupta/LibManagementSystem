
import java.io.IOException;
import UserPackage.*;
import BooksPackage.*;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class App extends Application {
    private double xOffset, yOffset;
    @Override
    public void start(Stage primaryStage) {
        
        
  Parent root;
try {
    root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
     Scene scene = new Scene(root);
      primaryStage.initStyle(StageStyle.UNDECORATED);

      root.setOnMousePressed(event -> {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    });

    root.setOnMouseDragged(event -> {
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    });
  
        primaryStage.setScene(scene);
        primaryStage.show();
    
} catch (IOException e) {
   
    e.printStackTrace();
}
    }
 
 
public static void main(String[] args) {
        launch(args);
    }
}