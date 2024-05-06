package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for the NewCategory.fxml file. This class handles the logic
 * and functionality of the UI components defined in the FXML file.
 */
public class ListAssetbyExpired {
	final String filePath_assets = "assets.csv";
	private List<AssetInfo> assets = new ArrayList<AssetInfo>();
	private AssetInfo selectedAsset;
	@FXML
	TextField assetName; // Text field for entering category name
	@FXML
	TableView<AssetInfo> tableView = new TableView<>(); //TableView for displaying asset information.
	
	@FXML
	TableColumn<AssetInfo, String> name; //TableColumn for displaying asset names

	@FXML
	TableColumn<AssetInfo, String> category; //TableColumn for displaying categories

	@FXML TableColumn<AssetInfo, String> locationName; //TableColumn for displaying asset locations

	@FXML
	TableColumn<AssetInfo, String> purchaseDate; //TableColumn for displaying purchase dates

	@FXML
	TableColumn<AssetInfo, String> description; //TableColumn for displaying asset descriptions

	@FXML 
	TableColumn<AssetInfo, String> purchaseValue; //TableColumn for displaying asset purchase value

	@FXML
	TableColumn<AssetInfo, String> warrantyExpirationDate; //TableColumn for displaying asset warranty expiration dates

	ObservableList<AssetInfo> list = FXCollections.observableArrayList(assets); //ObservableList for storing and managing asset information

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the FXML file has been loaded.
	 */
	public void initialize() {
		loadAssetsFromCSV();
		

		name.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("name"));
		category.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("category"));
		locationName.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("location"));
		purchaseDate.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("purchaseDate"));
		description.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("description"));
		purchaseValue.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("purchasedValue"));
		warrantyExpirationDate.setCellValueFactory(new PropertyValueFactory<AssetInfo, String>("warrantyExpirationDate"));
		

		tableView.setItems(list);
		
		searchAssetsByExpired();
	}
	
	/**
	 * Loads assets from a CSV file.
	 * 
	 * If the file exists, it reads each line from the file and processes it to create AssetInfo objects.
	 * If the file does not exist, an error message is printed.
	 */
	public void loadAssetsFromCSV() {
		// Path to the CSV file

		// Check if the file exists
		if (Files.exists(Paths.get(filePath_assets))) {
			try {
				// Read all lines from the file and process each line to create AssetInfo
				// objects

				assets = Files.readAllLines(Paths.get(filePath_assets)).stream().map(line -> line.split(","))
						.map(assetDetails -> parseAsset(assetDetails)).collect(Collectors.toList());

			} catch (IOException e) {
				System.err.println("Error reading CSV file: " + e.getMessage());
			}
		} else {
			System.err.println("CSV file does not exist");
		}
	}

	// Helper method to parse a single asset entry from the CSV
	/**
	 * Parses a single asset entry from the CSV file.
	 *
	 * @param assetDetails The array containing asset details to parse.
	 * @return The parsed AssetInfo object.
	 */
	private AssetInfo parseAsset(String[] assetDetails) {
		AssetInfo asset = new AssetInfo();
		asset.setName(assetDetails[0].trim());
		asset.setCategory(assetDetails[1].trim());
		asset.setLocation(assetDetails[2].trim());
		asset.setPurchaseDate(parseDate(assetDetails[3].trim()));
		asset.setDescription(assetDetails[4].trim());
		asset.setPurchasedValue(assetDetails[5].trim());
		asset.setWarrantyExpirationDate(parseDate(assetDetails[6].trim()));
		return asset;
	}

	// Helper method to parse dates, handling "No date provided"
	/**
	 * Parses a date string into a LocalDate object.
	 *
	 * @param date The date string to parse.
	 * @return The parsed LocalDate object, or null if the date string is "No date provided".
	 */
	private LocalDate parseDate(String date) {
		if (date.equals("No date provided")) {
			return null;
		} else {
			try {
				return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
			} catch (DateTimeParseException e) {
				System.err.println("Error parsing date: " + e.getMessage());
				return null;
			}
		}
	}

	/**
     * Handles the search action for assets by category.
     */
	@FXML
	public void searchAssetsByExpired() {
		LocalDate cur = LocalDate.now(); // This gets today's date

		ObservableList<AssetInfo> filteredAssets = FXCollections.observableArrayList();
		for (AssetInfo asset : assets) {
			System.out.println(asset.getName() + " : " + hasWarrantyExpired(cur, asset));
			if (hasWarrantyExpired(cur, asset)) {
				filteredAssets.add(asset);
			}
		}
		System.out.println("Number of matches: " + filteredAssets.size()); 
		tableView.setItems(filteredAssets);
	}
	
	/**
     * Checks if the warranty of an asset has expired relative to the current date.
     * 
     * @param asset The asset whose warranty expiration date is to be checked.
     * @return true if the warranty has expired, false otherwise.
     */
    public static boolean hasWarrantyExpired(LocalDate today, AssetInfo asset) {
        if (asset.getWarrantyExpirationLocalDate() != null) {
            return today.isAfter(asset.getWarrantyExpirationLocalDate()); // Check if today's date is after the expiration date
        }
        return false; // Return false if warrantyExpiration is null, implying no expiration date set
    }
	
	public void openAsset() {
        AssetInfo selectedAsset = tableView.getSelectionModel().getSelectedItem();
        if (selectedAsset != null) {
        	Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows until this one is closed
            dialog.setTitle("Asset Details");

            // Create labels and add them to a VBox
            VBox vbox = displayAssetInfo(selectedAsset);

            // Set the dialog pane content
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK); // Add an OK button to close the dialog

            // Show dialog and wait for response
            dialog.showAndWait();
        }
    }
	
	/**
	 * Displays asset information in a VBox layout.
	 * 
	 * @param arg The AssetInfo object containing the asset information.
	 * @return A VBox containing the asset information labels.
	 */
	public VBox displayAssetInfo(AssetInfo arg) {
		VBox res = new VBox(10);
		
		Label infoName = new Label("Name: " + arg.getName());
		Label infoCategory = new Label("Category: " + arg.getCategory());
		Label infoLocation = new Label("Location: " + arg.getLocation());
		Label infoPurchaseDate = new Label("Purchase Date: " + arg.getPurchaseDate());
		Label infoDescription = new Label("Description: " + arg.getDescription());
		Label infoValue = new Label("Value: " + arg.getPurchasedValue());
		Label infoWarrantyExpireDate = new Label("Warranty Expire Date: " + arg.getWarrantyExpirationDate());
		
		res.setAlignment(Pos.CENTER_LEFT);
		res.getChildren().addAll(infoName, infoCategory, infoLocation, infoPurchaseDate, infoDescription, infoValue, infoWarrantyExpireDate);
		
		return res;
	}
	

	// Overwrites deleted asset results to CSV file
	private void overWriteCSV() {
		clearCSV();
		for (AssetInfo itr : assets) {
			saveAssetToCSV(itr);
		}
	}

	// Clears the contents of the CSV file
	public void clearCSV() {
		// file path for the CSV file at instance variable
		try {
			// Write an empty list to the file, truncating it to zero size
			Files.write(Paths.get(filePath_assets), Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
			// testing message
			System.out.println("CSV file has been cleared.");
		} catch (IOException e) {
			// testing message
			System.err.println("Error clearing the CSV file: " + e.getMessage());
		}
	}

	/**
     * Saves the asset information to the CSV file.
     * 
     * @param asset The asset to be saved.
     */
	private void saveAssetToCSV(AssetInfo asset) {
		// file path for the CSV file at instance variable
		try {
			// Check if the file does not exist and create it if necessary
			if (!Files.exists(Paths.get(filePath_assets))) {
				Files.createFile(Paths.get(filePath_assets));
			}
			// Write the assets details to the CSV file
			try (FileWriter fw = new FileWriter(filePath_assets, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw)) {
				out.print(String.join(", ", asset.getName(), asset.getCategory(), asset.getLocation(),
						asset.getPurchaseDate(), // != null ?
													// asset.getPurchaseDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
													// : "",
						asset.getDescription(), asset.getPurchasedValue(), asset.getWarrantyExpirationDate()// != null ?
																											// asset.getWarrantyExpirationDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
																											// : ""
				));
				out.println();// Move to a new line in the file
			} catch (IOException e) {
				System.err.println("Error writing to CSV file: " + e.getMessage());// Handle possible I/O errors
			}
		} catch (IOException ex) {
			System.err.println("An error occurred initializing the CSV file: " + ex.getMessage());// Handle file
																									// creation errors
		}
	}

	
	/**
	 * Edits the selected asset.
	 * 
	 * If no asset is selected, a message is printed indicating that no item is selected.
	 */
	@FXML
	public void editSelectedAsset() {
		// Get the selected item from the TableView
		selectedAsset = tableView.getSelectionModel().getSelectedItem();

		if (selectedAsset != null) {
			// edit selected asset
			
			
			editAssetPage();
			

		} else {
			System.out.println("No item selected to edit.");
		}
		
	}

	// test if List correct loaded
	public void displayAll() {
		for (AssetInfo itr : assets) {
			itr.display();
			System.out.println();
		}
	}

	/**
	 * 
	 * Handles the action event when the "Back To Home" button is clicked. Loads the
	 * homepage FXML file and sets it as the scene for the stage.
	 */
	@FXML
	public void goHome() {
		try {// Load the FXML file for the welcome page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Welcome.fxml"));
			Parent root = loader.load();

			// Get the stage from the current scene
			Stage stage = (Stage) tableView.getScene().getWindow();

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

	/**
	 * Opens the edit asset page to modify the selected asset.
	 * 
	 * If an exception occurs during the loading of the FXML file or the initialization of the controller,
	 * the stack trace is printed.
	 */
	@FXML
	private void editAssetPage() {
			int index = assets.indexOf(selectedAsset);
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditAsset.fxml")); 
			Parent root = loader.load(); // Loads the root element of the FXML file
			
			EditAssetController editAssetController = loader.getController();
			editAssetController.initialize(assets, index);
			
			Stage stage = (Stage) tableView.getScene().getWindow();
			Scene scene = new Scene(root); // Creates a new scene with the loaded root element

			stage.setScene(scene); // Sets the scene to the stage
			stage.setTitle("Edit Asset"); // Sets the title of the stage
			stage.show(); // Shows the stage
		} catch (Exception e) {
			e.printStackTrace();// Prints the stack trace if an exception occurs
		}

	}

}
