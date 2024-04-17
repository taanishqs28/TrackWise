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
 * Controller class for the NewCategory.fxml file.
 * This class handles the logic and functionality of the UI components defined in the FXML file.
 */
public class NewCategoryController {
	
	@FXML TextField categoryNameLabel; // Text field for entering category name
	@FXML Label categoryDisplayLabel; // Label for displaying category information
	
	/**
     * Initializes the controller class. 
     * This method is automatically called after the FXML file has been loaded.
     */
	public void initialize() {
		// Set initial text for category display label
		categoryDisplayLabel.setText("No Category Defined Yet."); 
    }
	
	/**
     * Handles the action event when the "Add Category" button is clicked.
     * Reads the text entered in the categoryNameLabel text field and updates the categoryDisplayLabel accordingly.
     * If the categoryNameLabel is empty, displays an error message.
     */
	public void addCategory() {
		String categoryName = categoryNameLabel.getText(); // Get the text entered in the categoryNameLabel
		if (categoryName.isEmpty()) {
			// Display error message if category name is empty
			categoryDisplayLabel.setText("Error. Must Define Category Name!");
		}
		else {
			// Display the new category name
			categoryDisplayLabel.setText("New Category Defined: " + categoryName);
			saveCategoryToCSV(categoryName);
			categoryNameLabel.setText(""); // Clears the label once the categoryName is saved.
		}
		
	}
	
	/**
	 * Saves the entered category name to a CSV file.
	 * @param categoryName The name of the category to be saved.
	 */
	public void saveCategoryToCSV(String categoryName) {
	    String filePath = "categories.csv"; // The path to the CSV file.
	    
	    // Check if the file already exists, and if not, create it.
	    try {
	        if (!Files.exists(Paths.get(filePath))) {
	            Files.createFile(Paths.get(filePath));
	        }
	        
	        // Use FileWriter and BufferedWriter to write to the CSV file.
	        try (FileWriter fw = new FileWriter(filePath, true);
	             BufferedWriter bw = new BufferedWriter(fw);
	             PrintWriter out = new PrintWriter(bw)) {
	            out.println(categoryName); // Write the category name to the file.
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
	Loads the homepage FXML file and sets it as the scene for the stage.
	*/
	public void goHome() {
	    try {// Load the FXML file for the welcome page
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml"));
	        Parent root = loader.load();

	            // Get the stage from the current scene
	            Stage stage = (Stage) categoryNameLabel.getScene().getWindow();

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
