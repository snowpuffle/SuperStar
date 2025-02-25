package controllers.items;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.items.Item;

public class EditItemController implements Initializable {
	// Item for THIS Controller
	private Item item;

	// Main Attributes
	public TextField IDField;
	public TextField NameField;
	public TextField PriceField;
	public TextField QuantityField;
	public TextField BrandNameField;
	public TextField ExpirationDateField;
	public TextField ImageURLField;
	public ComboBox<String> StatusField;
	public ComboBox<String> TypeField;
	public ComboBox<String> isOrganicField;
	public ImageView ImageField;

	// Utility Attributes
	public Button SubmitButton;
	public Button GoBackButton;
	public Label MessageLabel;

	// Controller Helper
	private ItemControllerHelper ItemControllerHelper;

	// Default Class Constructor
	public EditItemController(Item item) {
		this.item = item;
	}

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Controller Helper
		this.ItemControllerHelper = new ItemControllerHelper();

		// Initialize Frame
		initializeFrame();

		// Initialize OnClick Action of Submit Button
		SubmitButton.setOnAction(event -> handleEditItem());
		GoBackButton.setOnAction(event -> handleGoBack());
	}

	// Initialize Frame
	private void initializeFrame() {
		initializeFields();
		StatusField.getItems().addAll("AVAILABLE", "NOT AVAILABLE");
		isOrganicField.getItems().addAll("YES", "NO");
		TypeField.setEditable(false);
		ImageURLField.setEditable(false);
	}

	// Initialize All Fields for Existing Item
	private void initializeFields() {
		if (item != null) {
			IDField.setText(String.valueOf(item.getID()));
			NameField.setText(item.getName());
			PriceField.setText(String.valueOf(item.getPrice()));
			TypeField.setValue(String.valueOf(item.getType()));
			QuantityField.setText(String.valueOf(item.getQuantity()));
			BrandNameField.setText(item.getBrandName());
			isOrganicField.setValue(item.isOrganic() ? "YES" : "NO");
			ExpirationDateField.setText(String.valueOf(item.getExpirationDate()));
			StatusField.setValue(String.valueOf(item.getStatus()));
			ImageURLField.setText(item.getImageURL());
			ImageField.setImage(ItemControllerHelper.getImage(item.getImageURL()));
		}
	}

	// Event: "Submit" Button is Clicked
	private void handleEditItem() {
		// Get New TextField Values
		String newPrice = PriceField.getText();
		String newName = NameField.getText();
		String newQuantity = QuantityField.getText();
		String newBrandName = BrandNameField.getText();
		String newIsOrganic = isOrganicField.getValue();
		String newExpirationDate = ExpirationDateField.getText();
		Item.ItemStatus newStatus = Item.ItemStatus.valueOf(StatusField.getValue());

		// Validate the New Values
		if (validateFields(newPrice, newName, newQuantity, newBrandName, newIsOrganic, newExpirationDate, newStatus)) {
			// Update Item ONLY if New Values are Valid
			editItem(newPrice, newName, newQuantity, newBrandName, newIsOrganic, newExpirationDate, newStatus);
		}

	}

	// Initialize Fields and Update Item to Database
	private void editItem(String newPrice, String newName, String newQuantity, String newBrandName, String newIsOrganic,
			String newExpirationDate, Item.ItemStatus newStatus) {

		// Convert String Values to Correct Types
		double price = ItemControllerHelper.parseDouble(newPrice);
		int quantity = ItemControllerHelper.parseInteger(newQuantity);
		boolean isOrganic = ItemControllerHelper.parseBoolean(newIsOrganic);
		LocalDate expirationDate = ItemControllerHelper.parseDate(newExpirationDate);

		// Set New Data to Item
		item.setPrice(price);
		item.setName(newName);
		item.setQuantity(quantity);
		item.setBrandName(newBrandName);
		item.setOrganic(isOrganic);
		item.setExpirationDate(expirationDate);
		item.setStatus(newStatus);

		// Update Item to Database by Accessing the Model
		Model.getInstance().editItem(item);

		// Reset the Form and Display Success Message
		handleMessageLabel("Item Updated to Database!", true);
	}

	// Validate All String Fields
	private boolean validateStringFields(String name, String brandName, Item.ItemStatus status) {
		// Initiate Flag
		boolean validated = true;

		// Validate String Value Fields
		if (!ItemControllerHelper.validateString(name)) {
			handleMessageLabel("Please Enter a Name!", false);
			validated = false;
		} else if (!ItemControllerHelper.validateString(brandName)) {
			handleMessageLabel("Please Enter a Brand Name!", false);
			validated = false;
		} else if (!ItemControllerHelper.validateChoice(String.valueOf(status))) {
			handleMessageLabel("Please Select a Status!", false);
			validated = false;
		}
		// return Flag
		return validated;
	}

	// Validate All Input Fields
	private boolean validateFields(String priceString, String name, String quantityString, String brandName,
			String isOrganicString, String expirationDateString, Item.ItemStatus status) {

		// Validate All Fields
		if (!validateStringFields(name, brandName, status)) {
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
		Model.getInstance().getViewFactory().showViewItemsFrame();
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) GoBackButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}