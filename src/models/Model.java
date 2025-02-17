package models;

import java.util.ArrayList;
import java.util.List;

import models.items.*;
import superstarsystem.DBManager;

public class Model {
	private static Model model;
	private ItemDAO itemDAO;

	public Model(DBManager dbManager) {
		this.itemDAO = new ItemDAO(dbManager.getConnection());

		ItemSeeds seeds = new ItemSeeds();
		seeds.generateRandomItems(10);
		ArrayList<Item> randomSeeds = seeds.getRandomItemList();

		// Add each randomly generated item to the database
		for (Item item : randomSeeds) {
			itemDAO.addItem(item);
		}

		// Retrieve all items from the database
		List<Item> items = itemDAO.getAllItems();

		// Display all items
		displayItems(items);
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

}