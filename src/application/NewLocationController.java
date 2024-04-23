package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Controller for managing the "New Location" form in the application.
 */
public class NewLocationController {

    @FXML
    private TextField locationNameLabel; // TextField for entering the name of the location

    @FXML
    private Label locationDisplayLabel; // Label for displaying feedback to the user

    @FXML
    private TextArea locationDescriptionLabel; // TextArea for entering the description of the location

    /**
     * Initializes the controller. This method is automatically called after the FXML fields are injected.
     */
    public void initialize() {
        locationDisplayLabel.setText("No location category defined yet."); // Set initial text for the display label
    }

    /**
     * Adds a new location category based on user input from the form.
     */
    public void addLocation() {
        String locationName = locationNameLabel.getText(); // Get the location name from the text field

        // Check if the location name is empty
        if (locationName.isEmpty()) {
            locationDisplayLabel.setText("Error. Must define location category name!"); // Show error message
        } else {
            locationDisplayLabel.setText("New location category defined: " + locationName); // Show success message

            // Use the Builder pattern to create a new LocationInfo object
            LocationInfo.Builder builder = new LocationInfo.Builder(locationName)
                    .setDescriptionFromTextField(locationDescriptionLabel.getText());

            LocationInfo location = builder.build(); // Build the LocationInfo object
            saveLocationCategoryToCSV(location); // Save the location to a CSV file

            // Clear the form fields
            locationNameLabel.setText("");
            locationDescriptionLabel.setText("");
        }
    }

    /**
     * Saves the location details to a CSV file.
     * @param location The location information to save.
     */
    public void saveLocationCategoryToCSV(LocationInfo location) {
        String filePath = "locations.csv"; // Define the file path for the CSV file

        try {
            // Check if the file does not exist and create it if necessary
            if (!Files.exists(Paths.get(filePath))) {
                Files.createFile(Paths.get(filePath));
            }

            // Write the location details to the CSV file
            try (FileWriter fw = new FileWriter(filePath, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(location.getName()); // Write the location name
                if (location.getDescription() != null && !location.getDescription().isEmpty()) {
                    out.print(", " + location.getDescription()); // Write the location description if present
                }
                out.println(); // Move to a new line in the file
            } catch (IOException e) {
                System.err.println("Error writing to CSV file: " + e.getMessage()); // Handle possible I/O errors
            }
        } catch (IOException ex) {
            System.err.println("An error occurred initializing the CSV file: " + ex.getMessage()); // Handle file creation errors
        }
    }

    /**
     * Navigates back to the home/welcome screen of the application.
     */
    public void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml")); // Load the welcome view
            Parent root = loader.load(); // Load the root element from the FXML
            Stage stage = (Stage) locationNameLabel.getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(root); // Create a new scene with the loaded root
            stage.setTitle("Welcome to TrackWise"); // Set the stage title
            stage.setScene(scene); // Set the new scene on the stage
            stage.show(); // Display the stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }
}
