package application;

/**
 * The LocationInfo class represents a location with a name and optional description.
 */
public class LocationInfo {
    private String name; // Holds the name of the location
    private String description; // Holds the description of the location

    /**
     * Private constructor to enforce the use of the Builder pattern.
     * @param builder The builder object containing all necessary data.
     */
    private LocationInfo(Builder builder) {
        this.name = builder.name; // Assign the name from the builder
        this.description = builder.description; // Assign the description from the builder
    }

    /**
     * Returns the name of the location.
     * @return A string representing the name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the location.
     * @return A string representing the description of the location.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The Builder static inner class for constructing a LocationInfo object.
     */
    public static class Builder {
        private String name; // Required parameter: name of the location
        private String description = ""; // Optional parameter: description of the location

        /**
         * Constructor for Builder with a mandatory name parameter.
         * @param name The name of the location.
         */
        public Builder(String name) {
            this.name = name; // Initialize the name of the location
        }

        /**
         * Sets the description for the location.
         * @param description A string containing the description.
         * @return The builder instance for chaining.
         */
        public Builder description(String description) {
            this.description = description; // Set the description
            return this; // Return the current Builder instance
        }

        /**
         * Sets the description from a text field input, ensuring it is not empty.
         * @param description The description string from the text field.
         * @return The builder instance for chaining.
         */
        public Builder setDescriptionFromTextField(String description) {
            if (!description.isEmpty()) { // Check if the description is not empty
                this.description = description; // Set the description if it contains text
            }
            return this; // Return the current Builder instance
        }

        /**
         * Constructs a new LocationInfo object using the current state of the Builder.
         * @return A new LocationInfo object initialized with Builder's data.
         */
        public LocationInfo build() {
            return new LocationInfo(this); // Create a new LocationInfo object using this Builder
        }
    }
}
