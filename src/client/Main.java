/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author andre
 */
public class Main extends Application {
    
   @Override
    public   void start(Stage primaryStage) throws IOException {
     //   Parent root = FXMLLoader.load(getClass().getResource("HomelessServiceFXML.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxmlGUI/LoginInformation.fxml"));
                              

        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Maleny Diary To Home System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
