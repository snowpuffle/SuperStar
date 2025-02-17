package superstarsystem;

import models.Model;

// import superstarsystem.DBManager;

public class Main {

	public static void main(String[] args) {

		// Establish Database Connection & Handle Initialization
		DBManager dbManager = new DBManager();

		Model model = new Model(dbManager);

		// Create ItemDAO to Perform CRUD Operations on Items
		// ItemDAO itemDAO = new ItemDAO(dbManager);

		//
		// Model model = new Model(itemDAO);

	}
}