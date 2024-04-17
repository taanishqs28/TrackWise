package application;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the NewLocation.fxml file.
 * This class handles the logic and functionality of the UI components defined in the FXML file.
 */
public class NewLocationController {
	
	@FXML TextField locationNameLabel; // Text field for entering location category name
	@FXML Label locationDisplayLabel; // Label for displaying location category information
	
	/**
     * Initializes the controller class. 
     * This method is automatically called after the FXML file has been loaded.
     */
	public void initialize() {
		// Set initial text for location category display label
		locationDisplayLabel.setText("No location category defined yet."); 
    }
	
	/**
     * Handles the action event when the "Add Location Category" button is clicked.
     * Reads the text entered in the locationNameLabel text field and updates the locationDisplayLabel accordingly.
     * If the locationNameLabel is empty, displays an error message.
     */
	public void addLocation() {
		String locationCategoryName = locationNameLabel.getText(); // Get the text entered in the locationNameLabel
		if (locationCategoryName.isEmpty()) {
			// Display error message if location category name is empty
			locationDisplayLabel.setText("Error. Must define location category name!");
		}
		else {
			// Display the new location category name
			locationDisplayLabel.setText("New location category defined: " + locationCategoryName);
			saveLocationCategoryToCSV(locationCategoryName); //Saves the location to CSV file
			locationNameLabel.setText(""); // Clears the text field once the locationName is saved.
		}
		
	}
	/**
	 * Saves the entered location category name to a CSV file.
	 * @param locationCategoryName The name of the location category to be saved.
	 */
	public void saveLocationCategoryToCSV(String locationCategoryName) {
	    String filePath = "location_categories.csv"; // The path to the CSV file.
	    
	    // Check if the file already exists, and if not, create it.
	    try {
	        if (!Files.exists(Paths.get(filePath))) {
	            Files.createFile(Paths.get(filePath));
	        }
	        
	        // Use FileWriter and BufferedWriter to write to the CSV file.
	        try (FileWriter fw = new FileWriter(filePath, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) {
	            out.println(locationCategoryName); // Write the location category name to the file.
	        } catch (IOException e) {
	            System.err.println("Error writing to CSV file: " + e.getMessage());
	            // Handle the exception appropriately, such as showing a dialog to the user.
	        }
	    } catch (IOException ex) {
	        System.err.println("An error occurred initializing the CSV file: " + ex.getMessage());
	        // Handle the exception appropriately.
	    }
	}
	
	/**
    
	Handles the action event when the "Back To Home" button is clicked.
	Loads the home-page FXML file and sets it as the scene for the stage.
	*/
	public void goHome() {
	    try {// Load the FXML file for the welcome page
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml"));
	        Parent root = loader.load();

	            // Get the stage from the current scene
	            Stage stage = (Stage) locationNameLabel.getScene().getWindow();

	            // Set the new scene for the stage
	            Scene scene = new Scene(root);

	            // Set the title of the stage
	            stage.setTitle("Welcome to TrackWise");

	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately
	        }
	    }
	
	
}
