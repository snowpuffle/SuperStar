package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Model;

public class DashboardController implements Initializable {

	// Buttons for Items
	public Button ViewItemsButton;
	public Button AddItemButton;
	public Button SearchItemButton;

	// Buttons for Transactions
	public Button ViewTransactionsButton;
	public Button AddTransactionButton;
	public Button SearchTransactionButton;

	// Utility Attributes
	public Button LogoutButton;

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {
		addListeners();
	}

	private void addListeners() {
		ViewItemsButton.setOnAction(event -> handleViewItems());
		AddItemButton.setOnAction(event -> handleAddItem());
//		SearchItemButton.setOnAction(event -> handleSearchAnimal());
//		ViewTransactionsButton.setOnAction(event -> handleViewProducts());
//		AddTransactionButton.setOnAction(event -> handleAddProduct());
//		SearchTransactionButton.setOnAction(event -> handleSearchProduct());
//		LogoutButton.setOnAction(event -> handleLogout());
	}

	// Event: "View All Items" Button is Clicked
	private void handleViewItems() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showViewItemsFrame();
	}

	// Event: "View All Items" Button is Clicked
	private void handleAddItem() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showAddItemFrame();
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) LogoutButton.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}

}