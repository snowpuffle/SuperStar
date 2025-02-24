package models.items;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemSeeds {
	private ArrayList<Item> listOfItems;
	private static Random random = new Random();

	// Item Attributes
	private static String[] TYPES = { "FRUITS", "VEGETABLES" };
	private static final String[] STATUS = { "AVAILABLE", "NOT AVAILABLE" };
	private static final String[] FRUIT_NAMES = { "Apple", "Banana", "Kiwi", "Orange", "Mango", "Watermelon",
			"Strawberry", "Lemon", "Grapes" };
	private static String[] VEGETABLE_NAMES = { "Beetroot", "Broccoli", "Cabbage", "Carrot", "Chard", "Corn",
			"Eggplant", "Ginger", "Mushrooms", "Spring Onion" };
	private static final String[] BRAND_NAMES = { "Fresh Harvest", "Nature's Best", "Green Farm", "Healthy Choice",
			"Organic Valley", "Pure Taste", "Golden Fields", "Farm Fresh", "Eco Produce", "Vital Greens" };

	// Fruit Images
	@SuppressWarnings("serial")
	private static final Map<String, String> FRUIT_IMAGES = new HashMap<String, String>() {
		{
			put("Apple", "apple.png");
			put("Banana", "banana.png");
			put("Cherries", "cherry.png");
			put("Kiwi", "kiwi.png");
			put("Orange", "orange.png");
			put("Mango", "mango.png");
			put("Watermelon", "watermelon.png");
			put("Strawberry", "strawberry.png");
			put("Lemon", "lemon.png");
			put("Grapes", "grapes.png");
		}
	};

	// Vegetable Images
	@SuppressWarnings("serial")
	private static final Map<String, String> VEGETABLE_IMAGES = new HashMap<String, String>() {
		{
			put("Beetroot", "beetroot.png");
			put("Broccoli", "broccoli.png");
			put("Cabbage", "cabbage.png");
			put("Carrot", "carrot.png");
			put("Chard", "chard.png");
			put("Corn", "corn.png");
			put("Eggplant", "eggplant.png");
			put("Ginger", "ginger.png");
			put("Mushrooms", "mushrooms.png");
			put("Spring Onion", "spring-onion.png");
		}
	};

	// Default Class Constructor
	public ItemSeeds() {
		// Create New List Instances
		this.listOfItems = new ArrayList<Item>();
	}

	// Generate Items
	public void generateItems(int numberOfItems) {

		// Generate X Number of Items
		for (int i = 0; i < numberOfItems; i++) {

			// Generate Random Attributes
			int ID = generateID();
			double price = generatePrice();
			String type = generateType();
			String name = generateName(type);
			int quantity = generateQuantity();
			String brandName = generateBrandName();
			boolean isOrganic = random.nextBoolean();
			LocalDate expirationDate = generateExpirationDate();
			String status = generateStatus();
			String imageURL = generateImageURL(type, name);

			// Create & Initialize Item Object
			Item item = new Item(ID, price, name, type, quantity, brandName, isOrganic, expirationDate, status,
					imageURL);
			listOfItems.add(item);
			// System.out.println(item.toString());
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
		return random.nextInt(maxPrice - minPrice + 1) + minPrice;
	}

	// Generate Random Type
	private String generateType() {
		return TYPES[random.nextInt(TYPES.length)];
	}

	// Generate Name based on Type
	private String generateName(String type) {
		String name = "";
		if ("FRUITS".equalsIgnoreCase(type)) {
			name = FRUIT_NAMES[random.nextInt(FRUIT_NAMES.length)];
		} else if ("VEGETABLES".equalsIgnoreCase(type)) {
			name = VEGETABLE_NAMES[random.nextInt(VEGETABLE_NAMES.length)];
		}
		return name;
	}

	// Generate Random Quantity
	public int generateQuantity() {
		Random random = new Random();
		int quantity = random.nextInt(30) + 1;
		return quantity;
	}

	// Generate Random Brand Name
	private String generateBrandName() {
		return BRAND_NAMES[random.nextInt(BRAND_NAMES.length)];
	}

	// Generate Random Expiration Date
	public LocalDate generateExpirationDate() {
		Random random = new Random();

		// Random Expiration 1 to 30 Days from Today
		int daysToAdd = random.nextInt(30) + 1;
		return LocalDate.now().plusDays(daysToAdd);
	}

	// Generate Random Status
	private String generateStatus() {
		return STATUS[random.nextInt(STATUS.length)];
	}

	// Generate ImageURL Based on Name
	private String generateImageURL(String type, String name) {

		// Initialize Empty Location
		String imageURL = "";

		// Set ImageURL based on Name & Type
		if ("FRUITS".equalsIgnoreCase(type)) {
			imageURL = FRUIT_IMAGES.getOrDefault(name, "\\icons\\warning.png");
			imageURL = "\\fruits\\" + imageURL;
		} else if ("VEGETABLES".equalsIgnoreCase(type)) {
			imageURL = VEGETABLE_IMAGES.getOrDefault(name, "\\icons\\warning.png");
			imageURL = "\\vegetables\\" + imageURL;
		} else {
			imageURL = "\\icons\\warning.png";
		}

		// Return ImageURL
		return imageURL;
	}

	// Get Item List
	public ArrayList<Item> getRandomItemList() {
		return listOfItems;
	}

}