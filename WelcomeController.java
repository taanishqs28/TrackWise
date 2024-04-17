package application;

import javafx.event.ActionEvent;
import javafx.scene.Node;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The WelcomeController class controls the behavior of the welcome page.
 * It handles actions such as navigating to other pages.
 */
public class WelcomeController {
    
    @FXML
    private Button enterCategoryButton; // Reference to the "enter category" button in the FXML file
    @FXML
    private Button enterLocationButton; // Reference to the "enter location" button in the FXML file

    /**
     * The initialize method is called after all @FXML annotated fields have been injected.
     * It can be used for initialization tasks.
     */
    @FXML
    private void initialize() {
        // Initialization code here
    }

    /**
     * The goToCategoryPage method is called when the "enter category" button is clicked.
     * It navigates to the NewCategory page.
     * 
     * @param event The ActionEvent triggered by clicking the button
     */
    @FXML
    private void goToCategoryPage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Gets the stage from the event source
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewCategory.fxml")); // Load the FXML file for Sample page
            Parent root = loader.load(); // Loads the root element of the FXML file
            Scene scene = new Scene(root); // Creates a new scene with the loaded root element
            stage.setScene(scene); // Sets the scene to the stage
            stage.setTitle("Define New Category"); // Sets the title of the stage
            stage.show(); // Shows the stage
        } catch (Exception e) {
            e.printStackTrace(); // Prints the stack trace if an exception occurs
        }
    }
    
    /**
     * The goToLocationPage method is called when the "enter location" button is clicked.
     * It navigates to the NewCategory page.
     * 
     * @param event The ActionEvent triggered by clicking the button
     */
    @FXML
    private void goToLocationPage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Gets the stage from the event source
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewLocation.fxml")); // Load the FXML file for Sample page
            Parent root = loader.load(); // Loads the root element of the FXML file
            Scene scene = new Scene(root); // Creates a new scene with the loaded root element
            stage.setScene(scene); // Sets the scene to the stage
            stage.setTitle("Enter New Location Name"); // Sets the title of the stage
            stage.show(); // Shows the stage
        } catch (Exception e) {
            e.printStackTrace(); // Prints the stack trace if an exception occurs
        }
    }
}