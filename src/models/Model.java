package models;

import java.util.List;

import models.items.*;
import superstarsystem.DBManager;
import views.ViewFactory;

public class Model {
	private static Model model;
	private final ViewFactory viewFactory;

	private ItemDAO itemDAO;

	// Default Class Constructor
	private Model() {
		this.viewFactory = new ViewFactory();
		this.itemDAO = new ItemDAO(DBManager.getInstance().getConnection());

		seedItems();
	}

	// Get Database Instance
	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	// Generate and Seed Items to Database
	private void seedItems() {

		// Initialize the Database with Random Seeds
		ItemSeeds seeds = new ItemSeeds();

		// Generate Seed Items
		seeds.generateItems(25);

		// Retrieve the List of Items from the ItemSeeds Object
		List<Item> listOfItems = seeds.getRandomItemList();

		// Iterate Over the List of Items and Add Each Item to the Database
		for (Item item : listOfItems) {
			itemDAO.addItem(item);
		}
	}

	public List<Item> getAllItems() {
		return itemDAO.getAllItems();
	}

	public boolean addItem(Item item) {
		return itemDAO.addItem(item);
	}

	public boolean editItem(Item item) {
		return itemDAO.editItem(item);
	}

	public Item getItemByID(int ID) {
		return itemDAO.getItemByID(ID);
	}

	public void deleteItem(Item item) {
		itemDAO.deleteItem(item);
	}

	// Get View Factory
	public ViewFactory getViewFactory() {
		return viewFactory;
	}
}