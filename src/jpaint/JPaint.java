/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaint;

import jpaint.controllers.JPaintMain;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author njoroge
 */
public class JPaint extends Application {

    JPaintMain main;
    AnchorPane root;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JPaint.class.getResource("ui/jpainmain.fxml"));
            root = loader.load();
            main = loader.getController();
            main.setStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setTitle("JPaint");
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            JPaint.showError("Error loading main GUI: \n"+e.getMessage(), "JPaint", Alert.AlertType.ERROR, primaryStage);
        }
    }
    public static Optional<ButtonType> showError(String message,String title,Alert.AlertType type,Window parent){
        Alert al=new Alert(type);
        al.setTitle(title);
        al.setContentText(title);
        al.setHeaderText(null);
        if(parent!=null){
            al.initModality(Modality.WINDOW_MODAL);
            al.initOwner(parent);
        }
        return al.showAndWait();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
