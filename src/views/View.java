package views;

import controller.Controller;
import models.items.Item;

import java.util.List;
import java.util.Scanner;

public class View {
	private Controller controller;
	private Scanner scanner;

	public View(Controller controller) {
		this.scanner = new Scanner(System.in);
		this.controller = controller;
	}

	// Display the Main Menu and Handle User Input
	public void start() {

		displayMenu();
		int choice = getUserChoice();

		switch (choice) {
		case 1 -> viewAllItems();
		case 0 -> {
			System.out.println("Goodbye!");
		}
		default -> System.out.println("ERROR: Invalid Choice.");
		}
	}

	// Display the main menu
	private void displayMenu() {
		System.out.println();
		System.out.println("-".repeat(40));
		System.out.println(formatTextWithPadding(" SUPERSTAR SYSTEM "));
		System.out.println("-".repeat(40));
		System.out.println("  << MAIN MENU >>");
		System.out.println("  1. View All Items");
		System.out.println("-".repeat(40));
		System.out.print("> Enter Selection: ");
	}

	// Get User Choice from the Main Menu
	private int getUserChoice() {
		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1; // Invalid Input
		}
	}

	// Feature: View all items
	private void viewAllItems() {
		controller.viewAllItems();
	}

	// Display the List of Items
	public void displayAllItems(List<Item> listOfItems) {

		// Print the Formatted Table Header
		System.out.println();
		printFormattedTableHeader();

		// Print Formatted Table Row
		printFormattedTableRow(listOfItems);
		printFormattedTableDivider();
	}

	// Print Formatted Table Header
	private void printFormattedTableHeader() {
		printFormattedTableDivider();
		System.out.printf("| %-6s | %-8s | %-28s | %-8s | %-8s | %-15s |\n", "ID", "Price", "Item Name", "Type",
				"Quantity", "Status");
		printFormattedTableDivider();
	}

	// Print Formatted Table Row
	private void printFormattedTableRow(List<Item> listOfItems) {

		// Print Each Item's Info in Row with 'X' Spaces Reserved
		for (Item item : listOfItems) {
			System.out.printf("| %-6d | %-8.2f | %-28s | %-8s | %-8d | %-15s |\n", item.getID(), item.getPrice(),
					item.getName(), item.getType(), item.getQuantity(), item.getStatus());
		}
	}

	// Print Formatted Table Divider
	private void printFormattedTableDivider() {

		// Initialize Divider with "+"
		String divider = "+";

		// Hold the Width of Each Column
		int[] columnWidths = { 6, 8, 28, 8, 8, 15 };

		// Generate "-" Characters for Each Column.
		for (int width : columnWidths) {
			divider += "-".repeat(width + 2) + "+";
		}

		// Print Table Divider
		System.out.println(divider);
	}

	private String formatTextWithPadding(String text) {
		int totalLength = 40; // Total length of the string (20 dashes + 20 dashes + text)

		// Calculate spaces to center the text
		int paddingLeft = (totalLength - text.length()) / 2;
		int paddingRight = totalLength - text.length() - paddingLeft;

		// Format the string to add padding and center it
		return " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
	}

}