package views;

import controller.Controller;
import models.items.Item;

import java.util.List;
import java.util.Scanner;

public class View {
	private Controller controller;
	private ViewHelper viewHelper;
	private ViewFormatter viewFormatter;

	// Default Class Controller
	public View(Controller controller) {
		this.controller = controller;
		this.viewFormatter = new ViewFormatter();
		this.viewHelper = new ViewHelper(viewFormatter);
	}

	// Display the Main Menu and Handle User Input
	public void start() {

		// Start the Program Until User Exits
		while (true) {

			// Display Main Menu
			displayMenu();

			// Get User Input
			int choice = viewHelper.getUserChoice();

			// Handle User Input
			switch (choice) {
			case 1 -> controller.viewAllItems();
			case 2 -> addNewItem();
			case 3 -> editExistingItem();
			case 4 -> editExistingItem();
			case 0 -> {
				System.out.println("Goodbye!");
				return;
			}
			default -> viewFormatter.printFormattedErrorMessage("Invalid Choice. Please Try Again!");
			}
		}
	}

	// Display the main menu
	private void displayMenu() {

		// Initialize Formatting Spacing
		int spacing = 80;

		// Print Formatted Menu Header
		viewFormatter.printFormattedHeader("SUPERSTAR SYSTEM MAIN MENU", spacing);

		// List All Menu Options
		viewFormatter.printFormattedMenuOption("[1] Display All Items in Database", spacing);
		viewFormatter.printFormattedMenuOption("[2] Add a New Item to Database", spacing);
		viewFormatter.printFormattedMenuOption("[3] Edit an Existing Item by ID", spacing);
		viewFormatter.printFormattedMenuOption("[4] Delete an Existing Item by ID", spacing);
		viewFormatter.printFormattedMenuOption("[0] Exit the Program", spacing);

		// Print Menu Divider
		viewFormatter.printFormattedMenuDivider(spacing);

		// Get User Input
		viewFormatter.printFormattedPrompt("Enter Selection: ");
	}

	// Handle Display the List of All Items
	public void displayAllItems(List<Item> listOfItems) {

		// Initialize Formatting Spacing
		int spacing = 94;

		// Print the Formatted Header
		viewFormatter.printFormattedHeader("DISPLAY ALL ITEMS ", spacing);
		viewFormatter.printFormattedTableHeader();

		// Print Formatted Table Rows
		for (Item item : listOfItems) {
			viewFormatter.printFormattedTableRow(item);
		}
		viewFormatter.printFormattedTableDivider();
	}

	// Handle Add New Item to Database
	private void addNewItem() {

		// Initialize Formatting Spacing
		int spacing = 80;

		// Print the Formatted Header
		System.out.println();
		viewFormatter.printFormattedHeader("ADD NEW ITEM", spacing);

		// Validate each input using helper methods
		double price = viewHelper.validateDoubleInput("Price", "Enter Item's Price: $", 0.00, 100.00);
		String name = viewHelper.validateStringInput("Name", "Enter Item's Name: ");
		String type = viewHelper.validateListOfOptions("Type", "Enter Item's Type: ", Item.getItemTypeOptions());
		int quantity = viewHelper.validateIntegerInput("Quantity", "Enter Item's Quantity: ", 0, 50);

		// Send User Input to Controller
		boolean success = controller.addNewItem(price, name, type, quantity);

		// Print Result Message
		if (success) {
			viewFormatter.printFormattedSuccessMessage("Item Added to Database!");
		} else {
			viewFormatter.printFormattedErrorMessage("Cannot Add Item to Database!");
		}
	}

	private void editExistingItem() {
		// Initialize Formatting Spacing
		int spacing = 81;

		// Print the Formatted Header
		System.out.println();
		viewFormatter.printFormattedHeader("EDIT AN EXISTING ITEM", spacing);

		boolean itemFound = false;
		int ID = -1;

		// Loop Until a Valid Item ID is Found
		while (!itemFound) {
			// Get Item ID from User
			ID = getItemByID();
			if (controller.getItemByID(ID) != null) {
				itemFound = true; // Valid Item Found
			} else {
				viewFormatter.printFormattedErrorMessage("Item NOT Found. Please Try Again!");
			}
		}

		// Prompt User to Edit the Item
		Object[] results = getUpdatedItemData();
		double price = (double) results[0];
		int quantity = (int) results[1];

		// Update the Item in the Controller
		boolean success = controller.editExistingItem(ID, price, quantity);

		// Print Result Message
		if (success) {
			viewFormatter.printFormattedSuccessMessage("Item Updated Successfully!");
		} else {
			viewFormatter.printFormattedErrorMessage("Failed to Update Item. Please Try Again!");
		}
	}

	// Handle Edit Existing Item from Database
	private int getItemByID() {
		return viewHelper.validateIntegerInput("ID", "Enter Item's ID: ", 10000, 99999);
	}

	private Object[] getUpdatedItemData() {
		double price = viewHelper.validateDoubleInput("Price", "Enter Item's New Price: $", 0.00, 100.00);
		int quantity = viewHelper.validateIntegerInput("Quantity", "Enter Item's New Quantity: ", 0, 50);

		return new Object[] { price, quantity };
	}

}