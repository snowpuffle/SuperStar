package views;

import controllers.*;
import controllers.items.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.items.Item;

public class ViewFactory {

	// Show User Welcome Frame
	public void showWelcomeFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/Welcome.fxml"));
		WelcomeController WelcomeController = new WelcomeController();
		fxmlLoader.setController(WelcomeController);
		createStage(fxmlLoader, "Welcome");
	}

	// Show Main Menu Window
	public void showDashboardFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/Dashboard.fxml"));
		DashboardController DashboardController = new DashboardController();
		fxmlLoader.setController(DashboardController);
		createStage(fxmlLoader, "Main Dashboard");
	}

	// Show View Items Window
	public void showViewItemsFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/items/ViewItems.fxml"));
		ViewItemsController ViewAnimalsController = new ViewItemsController();
		fxmlLoader.setController(ViewAnimalsController);
		createStage(fxmlLoader, "View All Items");
	}

	// Show View Items Window
	public void showAddItemFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/items/AddItem.fxml"));
		AddItemController AddItemController = new AddItemController();
		fxmlLoader.setController(AddItemController);
		createStage(fxmlLoader, "Add New Item");
	}

	// Show Edit Item Window
	public void showEditItemFrame(Item item) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/items/EditItem.fxml"));
		EditItemController EditItemController = new EditItemController(item);
		fxmlLoader.setController(EditItemController);
		createStage(fxmlLoader, "Edit Existing Item");
	}

	// Show Edit Item Window
	public void showSearchItemFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLs/items/SearchItem.fxml"));
		SearchItemController SearchItemController = new SearchItemController();
		fxmlLoader.setController(SearchItemController);
		createStage(fxmlLoader, "Search for Item by ID");
	}

	// Generic: Create Stage
	private void createStage(FXMLLoader fxmlLoader, String stageTitle) {
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("SuperStar System");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR: Issues with Creating Stage " + stageTitle + "!");
		}
	}

	// Generic: Close Stage
	public void closeStage(Stage stage) {
		stage.close();
	}

}