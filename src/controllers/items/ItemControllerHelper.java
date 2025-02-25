package controllers.items;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

import javafx.scene.image.Image;

public class ItemControllerHelper {

	// Utility Attributes
	private Random random;
	private static final String IMAGE_FOLDER = System.getProperty("user.dir") + "\\resources\\images";
	private static final String DEFAULT_IMAGE = "\\icons\\warning.png";

	// Default Class Constructor
	public ItemControllerHelper() {
		this.random = new Random();
	}

	// Generate Random ID
	public int generateRandomID() {
		int minID = 10000;
		int maxID = 99999;

		return random.nextInt(maxID - minID + 1) + minID;
	}

	// Validate Double Value
	public boolean validateDouble(String doubleString) {
		if (doubleString == null || doubleString.trim().isEmpty()) {
			return false; // Return False if Null / Empty
		}
		try {
			// Attempt to Parse to Double
			Double.parseDouble(doubleString.trim());
			return true;
		} catch (NumberFormatException e) {
			return false; // Return False if the Double is Invalid
		}
	}

	// Validate Choice Field
	public boolean validateChoice(String choice) {
		// Check if Choice Field is Empty
		if (choice == null || choice.isBlank()) {
			return false; // Return False if Null / Empty
		}
		return true;
	}

	// Validate String Value Field
	public boolean validateString(String stringValue) {
		// Check if String Field is Empty
		if (stringValue.isBlank() || stringValue.isEmpty()) {
			return false; // Return False if Blank / Empty
		}
		return true;
	}

	// Validate Integer Value
	public boolean validateInteger(String integerString) {
		// Check if Value is Empty or Null
		if (integerString == null || integerString.trim().isEmpty()) {
			return false; // Return False if Null / Empty
		}
		try {
			// Attempt to Parse to Integer
			Integer.parseInt(integerString.trim());
			return true;
		} catch (NumberFormatException e) {
			return false; // Return False if the Integer is Invalid
		}
	}

	// Validate Boolean Value
	public boolean validateBoolean(String booleanValue) {
		// Check if Value is Empty or Null
		if (booleanValue == null || booleanValue.trim().isEmpty()) {
			return false; // Return False if Null / Empty
		}

		// Check if Input is "Yes" or "No" or Invalid
		String normalizedInput = booleanValue.trim().toLowerCase();
		if (normalizedInput.equals("yes") || normalizedInput.equals("no")) {
			return true;
		} else {
			return false; // Return False if the Boolean is Invalid
		}
	}

	// Validate Date Value
	public boolean validateDate(String dateValue) {
		if (dateValue == null || dateValue.trim().isEmpty()) {
			return false; // Return False if Null / Empty
		}
		try {
			// Attempt to Parse to Formatted LocalDate
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate.parse(dateValue.trim(), dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false; // Return False if the Date is Invalid
		}
	}

	// Parse and Get Integer Value
	public int parseInteger(String integerString) {
		try {
			// Return Parsed Integer Value
			return Integer.parseInt(integerString.trim());
		} catch (NumberFormatException e) {
			return -1; // Return Default Value if Integer is Invalid
		}
	}

	// Parse and Get Double Value
	public double parseDouble(String doubleString) {
		try {
			// Return Parsed Double Value
			return Double.parseDouble(doubleString.trim());
		} catch (NumberFormatException e) {
			return -1; // Return Default Value if the Double is Invalid
		}
	}

	// Parse and Get Boolean Value
	public boolean parseBoolean(String booleanString) {
		// Check if Input is "Yes" or "No"
		String normalizedInput = booleanString.trim().toLowerCase();
		if (normalizedInput.equals("yes")) {
			return true;
		}
		return false;
	}

	// Parse and Get Date Value
	public LocalDate parseDate(String dateString) {
		try {
			// Return Parsed Date Value
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return LocalDate.parse(dateString.trim(), dateFormatter);
		} catch (DateTimeParseException e) {
			return null; // Return Null if the Date is Invalid
		}
	}

	// Get and Create Image
	public Image getImage(String imageName) {
		String imageURL = fixImage(imageName);
		return new Image(new File(imageURL).toURI().toString());
	}

	// Fix ImageURL
	public String fixImage(String image) {

		// Initialize Empty Location
		String imageLocation = "";

		// Set Default Image if Null or Empty
		if (image == null || image.trim().isEmpty()) {
			return IMAGE_FOLDER + DEFAULT_IMAGE;
		}

		// Set Image Location
		imageLocation = IMAGE_FOLDER + image;

		// Check if File Exists
		File file = new File(imageLocation);
		if (!file.exists()) {
			imageLocation = IMAGE_FOLDER + DEFAULT_IMAGE;
		}

		// Return Image Location
		return imageLocation;
	}
}