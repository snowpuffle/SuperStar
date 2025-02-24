package superstarsystem;

import javafx.application.Application;
import javafx.stage.Stage;
import models.Model;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Model.getInstance().getViewFactory().showWelcomeFrame();
	}

	public static void main(String[] args) {
		launch(args);
	}
}