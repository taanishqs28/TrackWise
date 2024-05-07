package application;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the JavaFX application.
 */
public class Main extends Application {
	private static Stage primaryStage;//test
	
	/**
     * The main method is the entry point for the Java application.
     * It launches the JavaFX application by calling the launch() method.
     * 
     * @param args The command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
    
    private static final String CSV_FILE_PATH = "assets.csv"; // Path to the CSV file containing asset information

    /**
     * The start method of the JavaFX application.
     * 
     * @param primaryStage The primary stage of the application.
     * @throws Exception If an error occurs during the start process.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Main.primaryStage = primaryStage;//test
        try {
            // Loads the home-page/welcome page
            FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml"));
            Parent welcomeRoot = welcomeLoader.load(); // Loads the FXML file into a Parent node
            //WelcomeController welcomeController = welcomeLoader.getController(); // Gets the controller associated with the FXML file
            Scene welcomeScene = new Scene(welcomeRoot, 629, 477); // Creates a new scene with the loaded root node and set its dimensions

            // Show the home-page/ welcome scene initially
            primaryStage.setScene(welcomeScene); // Sets the scene on the primary stage
            primaryStage.setTitle("Welcome to TrackWise"); // Sets the title of the primary stage
            primaryStage.show(); // Shows the primary stage
            
         // Check for expired warranties
            if (hasExpiredWarranties()) {
                System.out.println("Expired Warranties found.");
            	warrantyWarning();
            }
            else {
            	System.out.println("No expired Warranties.");
            }
        } catch(Exception e) {
            e.printStackTrace(); // Prints any exceptions that occur during the loading and showing process
        }
    }
    
    /**
     * Checks if there are expired warranties among the assets.
     *
     * @return True if expired warranties are found, false otherwise.
     */
    private boolean hasExpiredWarranties() {
        try {
            // Read asset information from the CSV file
            List<AssetInfo> assets = Files.readAllLines(Paths.get(CSV_FILE_PATH))
                    .stream()
                    .map(this::parseAssetInfo)
                    .collect(Collectors.toList());

            LocalDate today = LocalDate.now();
            for (AssetInfo asset : assets) {
                LocalDate warrantyExpirationDate = asset.getWarrantyExpirationLocalDate();
                if (warrantyExpirationDate != null && warrantyExpirationDate.isBefore(today)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Parses a line of text representing asset information from the CSV file.
     *
     * @param line The line of text to parse.
     * @return The parsed AssetInfo object.
     */
    private AssetInfo parseAssetInfo(String line) {
    	String[] parts = line.split(",");
        LocalDate purchaseDate = null;
        LocalDate warrantyExpirationDate = null;

        // Safely parse the purchase date if it is provided and valid
        if (!parts[3].trim().isEmpty() && !parts[3].trim().equalsIgnoreCase("No date provided")) {
            try {
                purchaseDate = LocalDate.parse(parts[3].trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid purchase date format for line: " + line);
            }
        }

        // Safely parse the warranty expiration date if it is provided and valid
        if (!parts[6].trim().isEmpty() && !parts[6].trim().equalsIgnoreCase("No date provided")) {
            try {
                warrantyExpirationDate = LocalDate.parse(parts[6].trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid warranty expiration date format for line: " + line);
            }
        }

        return new AssetInfo(
            parts[0].trim(),  // Name
            parts[1].trim(),  // Category
            parts[2].trim(),  // Location
            purchaseDate,     // Purchase date (can be null)
            parts[4].trim(),  // Description
            parts[5].trim(),  // Purchased value
            warrantyExpirationDate  // Warranty expiration date (can be null)
        );
    }
    
    /**
     * Displays a warranty warning dialog.
     * 
     * This method creates a dialog informing the user that there are assets whose warranties have expired.
     * It provides an option to show the list of expired assets.
     */
    private void warrantyWarning() {
    	Dialog<Void> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until this one is closed
        dialog.setTitle("Warranty Warning");

        // Create labels and add them to a VBox
        VBox vbox = new VBox(10);
        Label message = new Label("There are assets' warranty that has expired.");
        
        HBox buttonArea = new HBox(10);
        buttonArea.setAlignment(Pos.BOTTOM_RIGHT);
        Button showMe = new Button("Show me");
        showMe.setPrefSize(106, 28);
        
        buttonArea.getChildren().add(showMe);
        
        showMe.setOnAction(e -> {
        	dialog.close();
        	goToListAssetbyExpired(e);
        });
        
        vbox.getChildren().addAll(message, buttonArea);
        
        
        
        // Set the dialog pane content
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK); // Add an OK button to close the dialog

        // Show dialog and wait for response
        dialog.showAndWait();
    }
    
    /**
     * Redirects to the page displaying the list of assets by expiration.
     * 
     * @param event The action event triggered by the user.
     */
    @FXML
    private void goToListAssetbyExpired(ActionEvent event) {
    	Stage stage = Main.getPrimaryStage();//test
    	try {
    		//Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    		 FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ListAssetbyExpired.fxml")); // Load the FXML file for page
             Parent root = loader.load(); // Loads the root element of the FXML file
             Scene scene = new Scene(root); // Creates a new scene with the loaded root element
             stage.setScene(scene); // Sets the scene to the stage
             stage.setTitle("List Asset by Expired"); // Sets the title of the stage
             stage.show(); // Shows the stage
    	}catch(Exception e) {
    		e.printStackTrace();// Prints the stack trace if an exception occurs
    	}
    }
    
    /**
     * Retrieves the primary stage of the application.
     * 
     * @return The primary stage.
     */
    public static Stage getPrimaryStage() {
    	return Main.primaryStage;
    }

    
}
