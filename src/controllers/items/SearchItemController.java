package controllers.items;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Model;
import models.items.Item;

public class SearchItemController implements Initializable {
	// Main Attributes
	public TextField IDField;

	// Utility Attributes
	public Label MessageLabel;
	public Button GoBackButton;
	public Button SubmitButton;

	// Controller Helper
	private ItemControllerHelper ItemControllerHelper;

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		addListeners();
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {
		// Initialize Controller Helper
		this.ItemControllerHelper = new ItemControllerHelper();

		// Initialize OnClick Action of Submit Button
		GoBackButton.setOnAction(event -> handleGoBack());
		SubmitButton.setOnAction(event -> handleSearchItem());
	}

	// Event: "Submit" Button is Clicked
	private void handleSearchItem() {
		// Get ID TextField Value
		String stringID = IDField.getText();

		if (ItemControllerHelper.validateInteger(stringID)) {
			// Validate & Convert ID to Integer
			int ID = ItemControllerHelper.parseInteger(stringID);

			// Get Item based on ID
			Item item = getItem(ID);
			if (item != null) {
				closeCurrentWindow();
				Model.getInstance().getViewFactory().showEditItemFrame(item);
			} else {
				handleMessageLabel("Cannot Find Item with ID #" + ID + "!");
			}

		} else {
			handleMessageLabel("Please Enter a Valid ID - Must be a 5-Digit Number!");
		}
	}

	// Search Item by ID from Database using Model
	private Item getItem(int ID) {
		return Model.getInstance().getItemByID(ID);
	}

	// Event: "Go Back" Button is Clicked
	private void handleGoBack() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showDashboardFrame();
	}

	// Handle Message Label
	private void handleMessageLabel(String message) {
		MessageLabel.setText("ERROR: " + message);
		MessageLabel.setTextFill(Color.RED); // Set Text Color to Red for Error
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) GoBackButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}