package application;

import java.time.LocalDate;

/**
 * Class representing detailed information about an asset.
 */
public class AssetInfo {
    // Fields representing the details of an asset
    private String name;
    private String category;
    private String location;
    private LocalDate purchaseDate;
    private String description;
    private String purchasedValue;
    private LocalDate warrantyExpirationDate;
    
    /**
     * Default Constructor
     */
    public AssetInfo() {
    	//Default Constructor
    }
    
    /**
     * Constructor to initialize an AssetInfo object with all necessary details.
     *
     * @param name The name of the asset.
     * @param category The category under which the asset falls.
     * @param location The location where the asset is kept or used.
     * @param purchaseDate The date on which the asset was purchased.
     * @param description A brief description of the asset.
     * @param purchasedValue The monetary value of the asset at purchase.
     * @param warrantyExpirationDate The date when the asset's warranty expires.
     */
    public AssetInfo(String name, String category, String location, LocalDate purchaseDate, String description, String purchasedValue, LocalDate warrantyExpirationDate) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.purchaseDate = purchaseDate; // Directly set purchase date

        // Sets description; defaults to "No description provided" if input is null or blank
        this.description = (description == null || description.trim().isEmpty()) ? "No description provided" : description;

        // Sets purchased value; defaults to "No value provided" if input is null or blank
        this.purchasedValue = (purchasedValue == null || purchasedValue.trim().isEmpty()) ? "No value provided" : purchasedValue;

        this.warrantyExpirationDate = warrantyExpirationDate; // Directly set warranty expiration date
    }
    
    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Setter for category
    public void setCategory(String category) {
        this.category = category;
    }

    // Getter for location
    public String getLocation() {
        return location;
    }

    // Setter for location
    public void setLocation(String location) {
        this.location = location;
    }

    // Getter for purchase date that returns the date as a String or "No date provided" if null
    public String getPurchaseDate() {
        return purchaseDate != null ? purchaseDate.toString() : "No date provided";
    }
    public LocalDate getPurchaseLocalDate() {
    	return purchaseDate;
    }

    // Setter for purchase date
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    // Getter for description that returns the description as a String or "No description provided" if null
    public String getDescription() {
    	return description != null ? description.toString() : "No description provided";
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for purchased value that returns the purchased value as a String or "No value provided" if null
    public String getPurchasedValue() {
    	return purchasedValue != null ? purchasedValue.toString() : "No value provided";
    }

    // Setter for purchased value
    public void setPurchasedValue(String purchasedValue) {
        this.purchasedValue = purchasedValue;
    }

    // Getter for warranty expiration date that returns the date as a String or "No date provided" if null
    public String getWarrantyExpirationDate() {
        return warrantyExpirationDate != null ? warrantyExpirationDate.toString() : "No date provided";
    }
    public LocalDate getWarrantyExpirationLocalDate() {
    	return warrantyExpirationDate;
    }

    // Setter for warranty expiration date
    public void setWarrantyExpirationDate(LocalDate warrantyExpirationDate) {
        this.warrantyExpirationDate = warrantyExpirationDate;
    }
    
    
    //test purpose
    public void display() {
	    System.out.println("Asset Information:");
	    System.out.println("Name: " + (name != null ? name : "No name provided"));
	    System.out.println("Category: " + (category != null ? category : "No category provided"));
	    System.out.println("Location: " + (location != null ? location : "No location provided"));
	    System.out.println("Purchase Date: " + getPurchaseDate());
	    System.out.println("Description: " + getDescription());
	    System.out.println("Purchased Value: " + getPurchasedValue());
	    System.out.println("Warranty Expiration Date: " + getWarrantyExpirationDate());
	}
    
}
