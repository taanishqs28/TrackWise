package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the JavaFX application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Loads the home-page/welcome page
            FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
            Parent welcomeRoot = welcomeLoader.load(); // Loads the FXML file into a Parent node
            WelcomeController welcomeController = welcomeLoader.getController(); // Gets the controller associated with the FXML file
            Scene welcomeScene = new Scene(welcomeRoot, 629, 477); // Creates a new scene with the loaded root node and set its dimensions

            // Show the home-page/ welcome scene initially
            primaryStage.setScene(welcomeScene); // Sets the scene on the primary stage
            primaryStage.setTitle("Welcome to TrackWise"); // Sets the title of the primary stage
            primaryStage.show(); // Shows the primary stage
        } catch(Exception e) {
            e.printStackTrace(); // Prints any exceptions that occur during the loading and showing process
        }
    }
    /**
     * The main method is the entry point for the Java application.
     * It launches the JavaFX application by calling the launch() method.
     * 
     * @param args The command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}