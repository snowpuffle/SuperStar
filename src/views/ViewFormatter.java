package views;

import java.util.List;

import models.items.Item;

public class ViewFormatter {

	// Print Formatted Prompt
	public void printFormattedPrompt(String promptText) {
		System.out.printf("| >> %s", promptText);
	}

	// Print Formatted Error Message
	public void printFormattedErrorMessage(String errorText) {
		System.out.printf("\n| !! ERROR: %s", errorText);
		System.out.println();
	}
	
	// Print Formatted Success Message
		public void printFormattedSuccessMessage(String successText) {
			System.out.printf("| >> SUCCESS: %s", successText);
			System.out.println();
		}

	// Print Formatted List of Options
	public void printFormattedOptions(List<String> listOfOptions, int spacing) {
		printFormattedMenuDivider(spacing);
		for (int i = 0; i < listOfOptions.size(); i++) {
			printFormattedMenuOption("[" + (i + 1) + "] " + listOfOptions.get(i), spacing);
		}
		printFormattedMenuDivider(spacing);
	}

	// Print Formatted Menu Header
	public void printFormattedHeader(String headerText, int spacing) {

		// Print Menu Divider
		System.out.println();
		printFormattedMenuDivider(spacing);

		// Calculate the Remaining Space to Fill
		int padding = (spacing - headerText.length()) / 2;
		System.out.printf("|%" + padding + "s%s%" + padding + "s|\n", "", headerText, "");

		// Print Menu Divider
		printFormattedMenuDivider(spacing);
	}

	// Print Formatted Menu Divider
	public void printFormattedMenuDivider(int spacing) {
		System.out.println("+" + "-".repeat(spacing) + "+");
	}

	// Print Formatted Menu Option
	public void printFormattedMenuOption(String optionText, int spacing) {

		// Calculate the Remaining Space to Fill
		int padding = spacing - optionText.length() - 1;

		// Print Option Left-Aligned with Right-Padding
		System.out.printf("| %-" + (optionText.length()) + "s%" + padding + "s|\n", optionText, "");
	}

	// Print Formatted Table Header
	public void printFormattedTableHeader() {
		printFormattedTableDivider();
		System.out.printf("| %-6s | %-8s | %-28s | %-12s | %-8s | %-15s |\n", "ID", "Price", "Item Name", "Type",
				"Quantity", "Status");
		printFormattedTableDivider();
	}

	// Print Formatted Table Row
	public void printFormattedTableRow(Item item) {
		// Print Item's Info in Row with 'X' Spaces Reserved
		System.out.printf("| %-6d | %-8.2f | %-28s | %-12s | %-8d | %-15s |\n", item.getID(), item.getPrice(),
				item.getName(), item.getType(), item.getQuantity(), item.getStatus());
	}

	// Print Formatted Table Divider
	public void printFormattedTableDivider() {

		// Initialize Divider with "+"
		String divider = "+";

		// Hold the Width of Each Column
		int[] columnWidths = { 6, 8, 28, 12, 8, 15 };

		// Generate "-" Characters for Each Column.
		for (int width : columnWidths) {
			divider += "-".repeat(width + 2) + "+";
		}

		// Print Table Divider
		System.out.println(divider);
	}

}