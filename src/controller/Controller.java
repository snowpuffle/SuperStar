package controller;

import java.util.List;
import java.util.Random;

import views.View;
import models.Model;
import models.items.Item;
import superstarsystem.DBManager;

public class Controller {
	private View view;
	private Model model;

	private static Random random = new Random();

	// Default Class Constructor
	public Controller(DBManager dbManager) {
		this.model = new Model(dbManager);
		this.view = new View(this);

		view.start();
	}

	// View All Items in Database
	public void viewAllItems() {
		List<Item> items = model.getAllItems();
		view.displayAllItems(items);
	}

	// Add New Item to Database
	public boolean addNewItem(double price, String name, String type, int quantity) {
		Item item = new Item(generateID(), price, name, type, quantity, "AVAILABLE");
		return model.addItem(item);
	}

	// Edit Existing Item in Database
	public boolean editExistingItem(int ID, double price, int quantity) {
		Item item = getItemByID(ID);

		item.setPrice(price);
		item.setQuantity(quantity);

		return model.editItem(item);
	}

	// Get Existing Item by ID
	public Item getItemByID(int ID) {
		return model.getItemByID(ID);
	}

	// Generate Random ID
	private int generateID() {
		int minID = 10000;
		int maxID = 99999;

		return random.nextInt(maxID - minID + 1) + minID;
	}

}