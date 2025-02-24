module SuperStar {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;

	opens superstarsystem to javafx.graphics, javafx.fxml, javafx.base;
	opens controllers to javafx.graphics, javafx.fxml, javafx.base;
	opens controllers.items to javafx.graphics, javafx.fxml, javafx.base;
	opens models to javafx.graphics, javafx.fxml, javafx.base;
	opens models.items to javafx.graphics, javafx.fxml, javafx.base;

}