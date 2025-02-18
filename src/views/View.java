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
		System.out.println("-".repeat(40));
		System.out.println(formatTextWithPadding(" SUPERSTAR SYSTEM "));
		System.out.println("-".repeat(40));
		System.out.println("<< MAIN MENU >>");
		System.out.println("1. View All Items");
		System.out.println("-".repeat(40));
		System.out.print("> Enter Selection: ");
	}

	// Get user choice from the menu
	private int getUserChoice() {
		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1; // Invalid input
		}
	}

	// Feature: View all items
	private void viewAllItems() {
		controller.viewAllItems();
	}

	public void displayAllItems(List<Item> items) {
		System.out.println("Inventory:");
		for (Item item : items) {
			System.out.println(item);
		}
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