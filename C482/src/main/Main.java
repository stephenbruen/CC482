package main;
/** @author stephenbruen
 * Future enhancment would be making the program visually better looking and better enhance the search*/

import controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class creates the Product management system */
public class Main extends Application {

    @Override
    /** the stage for the application */
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/MainScreen.fxml"));
        Parent root = fxmlloader.load();
        MainScreenController mainScrCntrl = fxmlloader.getController();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /** launches the application */
    public static void main(String[] args) {
        launch(args);
    }
}