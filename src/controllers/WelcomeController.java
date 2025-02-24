package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Model;

public class WelcomeController implements Initializable {

	public AnchorPane RootPane;

	@Override
	// Initialize Method
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Set RootPane as Focus
		RootPane.setFocusTraversable(true);
		RootPane.requestFocus();

		// Add Event Listener for Key Presses
		RootPane.setOnMouseClicked(event -> handleNext());

	}

	// Handle Continue to Next Frame
	private void handleNext() {
		closeCurrentWindow();
		Model.getInstance().getViewFactory().showDashboardFrame();
	}

	// Generic: Close Current Window
	private void closeCurrentWindow() {
		// Get & Close the Current Window
		Stage stage = (Stage) RootPane.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}

}