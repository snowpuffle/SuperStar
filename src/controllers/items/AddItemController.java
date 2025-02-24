package controllers.items;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.items.Item;

public class AddItemController implements Initializable {

	private ItemControllerHelper ItemControllerHelper;

	// Main Attributes
	public TextField IDField;
	public TextField NameField;
	public TextField PriceField;
	public TextField QuantityField;
	public TextField BrandNameField;
	public TextField ExpirationDateField;
	public TextField ImageField;
	public ComboBox<String> StatusField;
	public ComboBox<String> TypeField;
	public ComboBox<String> isOrganicField;

	// Utility Attributes
	public Button SubmitButton;
	public Button GoBackButton;
	public Label MessageLabel;

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Frame
		initializeFrame();

		this.ItemControllerHelper = new ItemControllerHelper();

		// Initialize OnClick Action of Submit Button
		SubmitButton.setOnAction(event -> handleAddItem());
		GoBackButton.setOnAction(event -> handleGoBack());
	}

	// Initialize Frame
	private void initializeFrame() {
		// Initialize ComboBox Fields
		TypeField.getItems().addAll("FRUITS", "VEGETABLES");
		StatusField.getItems().addAll("AVAILABLE", "NOT AVAILABLE");
		isOrganicField.getItems().addAll("YES", "NO");
	}

	// Event: "Submit" Button is Clicked
	private void handleAddItem() {
		// Get TextField Values
		String price = PriceField.getText();
		String type = TypeField.getValue();
		String name = NameField.getText();
		String quantity = QuantityField.getText();
		String brandName = BrandNameField.getText();
		String isOrganic = isOrganicField.getValue();
		String expirationDate = ExpirationDateField.getText();
		String status = StatusField.getValue();
		String imageURL = ImageField.getText();

		// Continue to Add Item ONLY if Fields are ALL Validated
		if (validateFields(price, type, name, quantity, brandName, isOrganic, expirationDate, status, imageURL)) {
			addItem(price, type, name, quantity, brandName, isOrganic, expirationDate, status, imageURL);
		}
	}

	// Initialize Fields and Add Item to Database
	private void addItem(String priceString, String type, String name, String quantityString, String brandName,
			String isOrganicString, String expirationDateString, String status, String imageURL) {

		// Initialize Non-String Fields
		int ID = ItemControllerHelper.generateRandomID();
		double price = ItemControllerHelper.parseDouble(priceString);
		int quantity = ItemControllerHelper.parseInteger(quantityString);
		boolean isOrganic = ItemControllerHelper.parseBoolean(isOrganicString);
		LocalDate expirationDate = ItemControllerHelper.parseDate(expirationDateString);

		// Create Item and Add to Database
		Item item = new Item(ID, price, name, type, quantity, brandName, isOrganic, expirationDate, status, imageURL);
		boolean success = Model.getInstance().addItem(item);
		if (success) {
			handleMessageLabel("Item Added to Database", true);
		} else {
			handleMessageLabel("Cannot Add Item to Database!", false);
		}
	}

	// Validate All String Fields
	private boolean validateStringFields(String type, String name, String brandName, String status, String imageURL) {
		// Initiate Flag
		boolean validated = true;

		// Validate String Value Fields
		if (!ItemControllerHelper.validateString(name)) {
			handleMessageLabel("Please Enter a Name!", false);
			validated = false;
		} else if (!ItemControllerHelper.validateString(brandName)) {
			handleMessageLabel("Please Enter a Brand Name!", false);
			validated = false;
		} else if (!ItemControllerHelper.validateChoice(status)) {
			handleMessageLabel("Please Select a Status!", false);
			validated = false;
		} else if (!ItemControllerHelper.validateChoice(type)) {
			handleMessageLabel("Please Select a Type!", false);
			validated = false;
		} else if (!ItemControllerHelper.validateString(imageURL)) {
			handleMessageLabel("Please Enter an ImageURL!", false);
			validated = false;
		}
		// return Flag
		return validated;
	}

	// Validate All Input Fields
	private boolean validateFields(String priceString, String type, String name, String quantityString,
			String brandName, String isOrganicString, String expirationDateString, String status, String imageURL) {

		// Validate All Fields
		if (!validateStringFields(type, name, brandName, status, imageURL)) {
			return false;
		} else if (!ItemControllerHelper.validateDouble(priceString)) {
			handleMessageLabel("Please Enter a Valid Sale Price!", false);
			return false;
		} else if (!ItemControllerHelper.validateInteger(quantityString)) {
			handleMessageLabel("Please Enter a Valid Quantity!", false);
			return false;
		} else if (!ItemControllerHelper.validateBoolean(isOrganicString)) {
			handleMessageLabel("Please Select a Valid Organic Value!", false);
			return false;
		} else if (!ItemControllerHelper.validateDate(expirationDateString)) {
			handleMessageLabel("Please Enter a Valid Date!", false);
			return false;
		}

		// Return Flag
		return true;
	}

	// Handle Message Label
	private void handleMessageLabel(String message, boolean success) {
		if (success) {
			MessageLabel.setText("SUCCESS: " + message);
			MessageLabel.setTextFill(Color.GREEN); // Set Text Color to Green for Success
		} else {
			MessageLabel.setText("ERROR: " + message);
			MessageLabel.setTextFill(Color.RED); // Set Text Color to Red for Error
		}
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showDashboardFrame();
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) GoBackButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}