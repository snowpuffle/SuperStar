package views;

import java.util.List;
import java.util.Scanner;

public class ViewHelper {
	private Scanner scanner;
	private ViewFormatter viewFormatter;

	// Default Class Constructor
	public ViewHelper(ViewFormatter viewFormatter) {
		this.scanner = new Scanner(System.in);
		this.viewFormatter = viewFormatter;
	}

	// Get User Choice from the Main Menu
	public int getUserChoice() {
		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1; // Invalid Input
		}
	}

	// Validate String Input
	public String validateStringInput(String objectName, String promptText) {

		// Initialize Input
		String input;

		// Infinite Loop Until Valid Input is Provided
		while (true) {
			// Prompt User for Input & Parse the Input as a String
			viewFormatter.printFormattedPrompt(promptText);
			input = scanner.nextLine().trim();

			// ; Check if the Input is NOT Empty
			if (!input.isEmpty()) {
				break; // Valid Input
			}
			viewFormatter.printFormattedErrorMessage(objectName + " Cannot Be Empty. Please Try Again!");
		}

		// Return Valid Input
		return input;
	}

	// Validate Double Input
	public double validateDoubleInput(String objectName, String promptText, double min, double max) {

		// Initialize Input
		double input = -1;

		// Infinite Loop Until Valid Input is Provided
		while (true) {
			try {
				// Prompt User for Input & Parse the Input as a Double
				viewFormatter.printFormattedPrompt(promptText);
				input = Double.parseDouble(scanner.nextLine());

				// Check if the Input is Within the Specified Range
				if (input > min && input < max) {
					break; // Valid Input
				} else {
					// Input is Out of Range
					viewFormatter.printFormattedErrorMessage(
							objectName + " must be > " + min + " and < " + max + ". Please Try Again!");
				}
			} catch (NumberFormatException e) {
				// Catch Invalid Input that Cannot be Parsed as a Double
				viewFormatter.printFormattedErrorMessage(
						"Invalid Input. Please Enter a Valid Number for the " + objectName + ".");
			}
		}

		// Return Valid Input
		return input;
	}

	// Validate List of Options
	public String validateListOfOptions(String objectName, String promptText, List<String> listOfOptions) {

		// Initialize Variables
		int input = -1;
		String selectedOption = "";
		int spacing = 80;

		// Infinite Loop Until Valid Input is Provided
		while (true) {
			try {
				// Prompt User for Input
				viewFormatter.printFormattedOptions(listOfOptions, spacing);
				viewFormatter.printFormattedPrompt(promptText);
				input = Integer.parseInt(scanner.nextLine());

				// Check if the Input is Within the Valid Range
				if (input > 0 && input <= listOfOptions.size()) {
					// Get the Option Based on Input Index
					selectedOption = listOfOptions.get(input - 1);
					break; // Exit Loop on Valid Input
				} else {
					viewFormatter
							.printFormattedErrorMessage("Invalid " + objectName + ". Please Select a Valid Option!");
				}
			} catch (NumberFormatException e) {
				viewFormatter.printFormattedErrorMessage(
						"Invalid Input. Please Enter a Number Corresponding to a " + objectName);
			}
		}

		return selectedOption; // Return the Selected Option
	}

	// Validate Double Input
	public int validateIntegerInput(String objectName, String promptText, int min, int max) {

		// Initialize Input
		int input = -1;

		// Infinite Loop Until Valid Input is Provided
		while (true) {
			try {
				// Prompt User for Input & Parse the Input as an Integer
				viewFormatter.printFormattedPrompt(promptText);
				input = Integer.parseInt(scanner.nextLine());

				// Check if the Input is Within the Specified Range
				if (input > min && input < max) {
					break; // Valid Input
				} else {
					// Input is Out of Range
					viewFormatter.printFormattedErrorMessage(
							objectName + " must be > " + min + " and < " + max + ". Please Try Again!");
				}
			} catch (NumberFormatException e) {
				// Catch Invalid Input that Cannot be Parsed as a Double
				viewFormatter.printFormattedErrorMessage(
						"Invalid Input. Please Enter a Valid Number for the " + objectName + ".");
			}
		}

		// Return Valid Input
		return input;
	}

}