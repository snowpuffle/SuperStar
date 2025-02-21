package models;

import java.util.ArrayList;
import java.util.List;

import models.items.*;
import superstarsystem.DBManager;

public class Model {
	private static Model model;
	private ItemDAO itemDAO;

	// Default Class Constructor
	public Model(DBManager dbManager) {
		// Create ItemDAO Object
		this.itemDAO = new ItemDAO(dbManager.getConnection());

		// Seed the Database with Random Seeds
		generateSeeds(itemDAO);
	}

	// Seed the Database
	private static void generateSeeds(ItemDAO itemDAO) {

		// Initialize the Database with Random Seeds
		ItemSeeds seeds = new ItemSeeds();
		seeds.generateRandomItems(10);
		ArrayList<Item> randomSeeds = seeds.getRandomItemList();

		// Add Each Seed to the Database
		if (itemDAO.getAllItems().isEmpty()) {
			for (Item item : randomSeeds) {
				itemDAO.addItem(item);
			}
			// System.out.println("Database Seeded with Random Items.");
		} else {
			// System.out.println("Database Already Contains Data - Skipping Seeding.");
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

	private static void displayItems(List<Item> items) {
		System.out.println("\nItems in the Database:");
		System.out.println("--------------------------------------");
		for (Item item : items) {
			System.out.printf("ID: %d | Name: %s | Price: $%.2f | Quantity: %d%n", item.getID(), item.getName(),
					item.getPrice(), item.getQuantity());
		}
		System.out.println("--------------------------------------");
	}

	// Get the Singleton Instance
	public static Model getInstance(DBManager dbManager) {
		if (model == null) {
			model = new Model(dbManager);
		}
		return model;
	}
}