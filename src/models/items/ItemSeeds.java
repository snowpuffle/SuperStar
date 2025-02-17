package models.items;

import java.util.ArrayList;
import java.util.Random;

public class ItemSeeds {
	private ArrayList<Item> listOfItems;
	private static Random random = new Random();

	// Product Attributes
	private static String[] TYPES = { "FRUITS", "VEGGIES" };
	private static String[] STATUS = { "AVAILABLE", "NOT AVAILABLE" };

	private static String[] FRUIT_NAMES = { "Apple Delight", "Banana Bliss", "Charming Cherries", "Golden Pineapple",
			"Crisp Pears", "Juicy Oranges", "Lush Mangoes", "Ripe Watermelon", "Sweet Strawberries", "Zesty Lemons",
			"Tropical Kiwi", "Fresh Grapes", "Plump Blueberries", "Blackberry Burst", "Perfect Peaches",
			"Tangy Tangerines", "Sunny Papayas", "Melon Medley", "Rich Avocado", "Starfruit Surprise",
			"Dragonfruit Dream", "Vibrant Pomegranate", "Cranberry Crisp", "Lively Limes", "Raspberry Bliss",
			"Juicy Nectarines", "Cantaloupe Carnival", "Passionfruit Punch", "Exotic Guava" };

	private static String[] VEGGIE_NAMES = { "Crisp Lettuce", "Hearty Spinach", "Golden Carrots", "Fresh Broccoli",
			"Sweet Corn", "Zucchini Delight", "Bell Pepper Medley", "Garden Cucumbers", "Savory Cauliflower",
			"Spicy Jalapeños", "Tender Green Beans", "Plump Tomatoes", "Earthy Beets", "Rich Butternut Squash",
			"Golden Potatoes", "Ruby Red Radishes", "Vibrant Eggplant", "Garlic Cloves", "Peppery Arugula",
			"Crispy Celery", "Sun-Kissed Peas", "Brussels Sprouts", "Ginger Roots", "Bright Red Chili Peppers",
			"Fresh Asparagus", "Leafy Kale", "Wild Mushrooms", "Sweet Onions", "Pumpkin Patch Picks",
			"Herbal Parsley" };

	// Default Class Constructor
	public ItemSeeds() {
		// Create New List Instances
		this.listOfItems = new ArrayList<Item>();
	}

	// Generate Random Item Seeds
	public void generateRandomItems(int numberOfItems) {
		// Generate X Number of Items
		for (int i = 0; i < numberOfItems; i++) {

			// Generate Attributes
			int ID = generateID();
			String type = TYPES[random.nextInt(TYPES.length)];
			double price = generatePrice();
			String name = generateName(type);
			int quantity = generateQuantity();
			String status = STATUS[random.nextInt(STATUS.length)];

			// Create & Initialize Item Object
			Item item = new Item(ID, price, name, type, quantity, status);
			listOfItems.add(item);
			// System.out.println(product.toString());
		}
	}

	// Generate Random ID
	public int generateID() {
		int minID = 10000;
		int maxID = 99999;

		return random.nextInt(maxID - minID + 1) + minID;
	}

	// Generate Random Price
	public double generatePrice() {
		int minPrice = 1;
		int maxPrice = 50;
		double price = random.nextInt(maxPrice - minPrice + 1) + minPrice;

		return price;
	}

	// Generate Name based on Type
	public String generateName(String type) {
		String name = "";
		if ("FRUITS".equalsIgnoreCase(type)) {
			name = FRUIT_NAMES[random.nextInt(FRUIT_NAMES.length)];
		} else if ("VEGGIES".equalsIgnoreCase(type)) {
			name = VEGGIE_NAMES[random.nextInt(VEGGIE_NAMES.length)];
		}
		return name;
	}

	// Generate Random Quantity
	public int generateQuantity() {
		Random random = new Random();
		int quantity = random.nextInt(30) + 1;
		return quantity;
	}

	// Get Item List
	public ArrayList<Item> getRandomItemList() {
		return listOfItems;
	}

}