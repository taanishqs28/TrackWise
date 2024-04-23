package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the New Asset page, handling interactions with the form where users can enter details
 * about a new asset including name, category, location, description,purchase date, purchased value,and warranty expiration date .
 * It validates required fields, saves asset information to a CSV file, and navigates among views.
 */

public class NewAssetController {
	
	 	@FXML private TextField assetNameLabel; // Text field for entering assetName
	 	@FXML private Label assetDisplayLabel; // Label for displaying the asset information
	 	
	    @FXML private ComboBox<String> categoryComboBox;// Dropdown box for categories defined
	    
	    @FXML private ComboBox<String> locationComboBox;// Dropdown box for locations defined
	    
	    @FXML private DatePicker purchaseDatePicker; // Date chooser for Purchase date if any
	    
	    @FXML private TextArea descriptionTextArea; // Text Area for description of the asset
	    
	    @FXML private TextField purchasedValueTextField; // Text Field for the Purchased Value
	    
	    @FXML private DatePicker warrantyExpirationDatePicker;// Date chooser for warranty expiration date if any
	    
	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded. It calls methods to populate the category
	     * and location dropdowns.
	     */
	    
	    public void initialize() {
	        loadCategories();
	        loadLocations();
	    }
	    
	    /**
	     * Loads category options from a CSV file into the category combo box.
	     */

	    private void loadCategories() {
	        try {
	        	// Reads all lines from categories.csv and collects them into a List
	            List<String> categories = Files.readAllLines(Paths.get("categories.csv")).stream().collect(Collectors.toList());
	            categoryComboBox.getItems().setAll(categories);//Sets items in categoryComboBox
	        } catch (IOException e) {
	            System.err.println("Error loading categories: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Loads location options from a CSV file into the location combo box.
	     */

	    private void loadLocations() {
	        try {
	        	// Reads all lines from locations.csv and collects them into a List
	            List<String> locations = Files.readAllLines(Paths.get("locations.csv")).stream().collect(Collectors.toList());
	            locationComboBox.getItems().setAll(locations);// Sets items in locationComboBox
	        } catch (IOException e) {
	            System.err.println("Error loading locations: " + e.getMessage());
	        }
	    }
	    
	    /**
	     * Handles the action to save an asset.
	     * Validates required fields and saves asset data to a CSV file.
	     */

	    @FXML
	    private void saveAsset() {
	        if (assetNameLabel.getText().isEmpty() || categoryComboBox.getValue() == null || locationComboBox.getValue() == null) {
	        	assetDisplayLabel.setText("Error: Name, Category, and/or Location are required.");
	        } else {
	        	// incorrect save to csv file, change to not put description in assets.csv
	        	String location = locationComboBox.getValue().contains(",") ? locationComboBox.getValue().split(",")[0] : locationComboBox.getValue();
	            AssetInfo asset = new AssetInfo(assetNameLabel.getText(), categoryComboBox.getValue(),
	            		location, purchaseDatePicker.getValue(), descriptionTextArea.getText(),
	                    purchasedValueTextField.getText(), warrantyExpirationDatePicker.getValue());

	            assetDisplayLabel.setText("New Asset Defined: " + assetNameLabel.getText());
	            saveAssetToCSV(asset);
	            clearForm();
	            
	        }
	    }
	    /**
		 * Saves the entered asset information to a CSV file if already exist else makes a new CSV file.
		 * @param asset The asset to save.
		 */

	    private void saveAssetToCSV(AssetInfo asset) {
	        String filePath = "assets.csv";// Define the file path for the CSV file


	        try {
	        	// Check if the file does not exist and create it if necessary
	            if (!Files.exists(Paths.get(filePath))) {
	                Files.createFile(Paths.get(filePath));
	            }
	            // Write the assets details to the CSV file
	            try (FileWriter fw = new FileWriter(filePath, true);
	                 BufferedWriter bw = new BufferedWriter(fw);
	                 PrintWriter out = new PrintWriter(bw)) {
	                out.print(String.join(", ",
	                    asset.getName(),
	                    asset.getCategory(),
	                    asset.getLocation(),
	                    asset.getPurchaseDate(),// != null ? asset.getPurchaseDate().format(DateTimeFormatter.ISO_LOCAL_DATE) : "",
	                    asset.getDescription(),
	                    asset.getPurchasedValue(),
	                    asset.getWarrantyExpirationDate()// != null ? asset.getWarrantyExpirationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) : ""
	                ));
	                out.println();// Move to a new line in the file
	            } catch (IOException e) {
	                System.err.println("Error writing to CSV file: " + e.getMessage());// Handle possible I/O errors
	            }
	        } catch (IOException ex) {
	            System.err.println("An error occurred initializing the CSV file: " + ex.getMessage());// Handle file creation errors
	        }
	    }
	    /**
	     * Clears all form fields after saving or when reset is needed.
	     */
	    private void clearForm() {
	    	assetNameLabel.setText("");
	        categoryComboBox.setValue("Category: ");
	        locationComboBox.setValue("Location: ");
	        purchaseDatePicker.setValue(null);
	        descriptionTextArea.setText("");
	        purchasedValueTextField.setText("");
	        warrantyExpirationDatePicker.setValue(null);
	    }
	    
	    /**
	     * Handles the action event when the "Back To Home" button is clicked.
	     * Loads the homepage FXML file and sets it as the scene for the stage.
	     */
	    @FXML
	    private void goHome() {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml"));
	            Parent root = loader.load();
	            Stage stage = (Stage) assetNameLabel.getScene().getWindow();
	            Scene scene = new Scene(root);
	            stage.setTitle("Welcome Home");
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}
