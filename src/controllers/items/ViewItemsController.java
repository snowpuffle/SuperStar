package controllers.items;

import java.io.File;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Model;
import models.items.Item;

public class ViewItemsController implements Initializable {

	// List of All Items
	private List<Item> ListOfItems;

	// Attributes for Table of Items
	public TableView<Item> TableOfItems;
	public TableColumn<Item, Integer> ColumnID;
	public TableColumn<Item, String> ColumnName;
	public TableColumn<Item, String> ColumnType;
	public TableColumn<Item, String> ColumnBrandName;
	public TableColumn<Item, String> ColumnQuantity;
	public TableColumn<Item, Double> ColumnPrice;

	// Attributes for Item Card
	public Label IDField;
	public Label PriceField;
	public Label TypeField;
	public Label NameField;
	public Label QuantityField;
	public Label BrandNameField;
	public Label isOrganicField;
	public Label ExpirationDateField;
	public Label StatusField;
	public ImageView ImageField;

	// Utility Attributes
	public Button GoBackButton;
	public Button EditItemButton;

	private ItemControllerHelper ItemControllerHelper;

	// Initialize Method
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.ItemControllerHelper = new ItemControllerHelper();
		initializeTable();
		addListeners();
	}

	// Initialize Cell Values and Data for Table
	private void initializeTable() {
		ColumnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
		ColumnBrandName.setCellValueFactory(new PropertyValueFactory<>("brandName"));
		ColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		ColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		displayItemData();
	}

	// Initialize OnClick Actions for All Buttons
	private void addListeners() {

		// Set a Listener on Table View to Update Item Data When a Row is Selected
		TableOfItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				updateItemCard(newSelection);
			}
		});

		// Initialize OnClick Action of Submit Button
		EditItemButton.setOnAction(event -> handleEditItem());
		GoBackButton.setOnAction(event -> handleGoBack());
	}

	// Display Item Data to Table
	public void displayItemData() {

		// Attempt to Display Item Data to Table
		try {
			// Get List of Items by Accessing the Model
			ListOfItems = Model.getInstance().getAllItems();

			// Send Data to Table
			ObservableList<Item> data = FXCollections.observableList(ListOfItems);
			TableOfItems.setItems(data);

			// Select the First Row
			TableOfItems.getSelectionModel().selectFirst();

		} catch (Exception e) {
			showErrorDialog("ERROR: Cannot Get List of Items from the Database!");
		}
	}

	// Update Item Card
	private void updateItemCard(Item item) {
		// No Item Selected
		if (item == null) {
			showErrorDialog("ERROR: No Item Selected!");
			return;
		}

		// Attempt to Update Selected Card
		try {
			// Set Default Values to Prevent Errors
			String id = String.valueOf(item.getID());
			String price = getPrice(item.getPrice());
			String name = getOrDefault(item.getName(), "Unknown");
			String type = getOrDefault(item.getType(), "Unknown");
			String quantity = String.valueOf(item.getQuantity());
			String brandName = getOrDefault(item.getBrandName(), "Unknown");
			String isOrganic = item.isOrganic() ? "Yes" : "No";
			String expirationDate = getOrDefault(String.valueOf(item.getExpirationDate()), "N/A");
			String status = getOrDefault(item.getStatus(), "Unknown");
			Image image = ItemControllerHelper.getImage(item.getImageURL());

			// Update UI fields
			IDField.setText(id);
			PriceField.setText(price);
			NameField.setText(name);
			TypeField.setText(type);
			QuantityField.setText(quantity);
			BrandNameField.setText(brandName);
			isOrganicField.setText(isOrganic);
			ExpirationDateField.setText(expirationDate);
			StatusField.setText(status);
			ImageField.setImage(image);

		} catch (Exception e) {
			showErrorDialog("ERROR: Occurred while Updating the Card!" + e);
		}
	}

	// Get Formatted Price
	private String getPrice(double price) {
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		return price > 0 ? currencyFormat.format(price) : "N/A";
	}

	// Get Default if Given Value is Null
	private String getOrDefault(String value, String defaultValue) {
		return value != null ? value : defaultValue;
	}

	// Event: Handle Edit Item Button
	private void handleEditItem() {
		Item selectedItem = TableOfItems.getSelectionModel().getSelectedItem();
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showEditItemFrame(selectedItem);
	}

	// Display Error Messages
	private void showErrorDialog(String message) {
		System.out.println(message);
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