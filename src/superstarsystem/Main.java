package superstarsystem;

import controller.Controller;
import views.View;

// import superstarsystem.DBManager;

public class Main {

	public static void main(String[] args) {

		try {
			// Initialize Database Manager
			DBManager dbManager = new DBManager();

			// Initialize Controller
			new Controller(dbManager);

		} catch (Exception e) {
			System.out.println("An error occurred while starting the Supermarket System:");
			e.printStackTrace();
		}

	}
}