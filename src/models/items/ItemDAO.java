package models.items;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
	private final Connection connection;

	// SQL Queries
	private static final String GET_ALL_ITEMS = "SELECT * FROM Items";
	private static final String GET_ITEM_BY_ID = "SELECT * FROM Items WHERE ID = ?";
	private static final String ADD_ITEM = "INSERT INTO Items (ID, Price, Name, Type, Quantity, BrandName, isOrganic, ExpirationDate, Status, ImageURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_ITEM = "UPDATE Items SET Price = ?, Name = ?, Quantity = ?, BrandName =?, isOrganic = ?, ExpirationDate = ?, Status = ? WHERE ID = ?";
	private static final String DELETE_ITEM = "DELETE FROM Items WHERE ID = ?";

	// Default Class Constructor
	public ItemDAO(Connection connection) {
		this.connection = connection;
	}

	// Get All Items from Database
	public List<Item> getAllItems() {

		// Initialize Empty List
		List<Item> listOfItems = new ArrayList<>();

		// Initialize SQL Components
		PreparedStatement statement = null;
		ResultSet results = null;

		// Attempt to Get All Items from Database
		try {
			// Prepare SQL Statement
			statement = connection.prepareStatement(GET_ALL_ITEMS);

			// Execute Statement & Handle Results
			results = statement.executeQuery();
			while (results.next()) {
				Item item = addItemFromResultSet(results);
				listOfItems.add(item);
			}

		} catch (SQLException e) {
			System.out.println("ERROR ItemDAO: Cannot Get Items!");
		}

		// Return List of Items
		return listOfItems;
	}

	// Get Product By ID From Database
	public Item getItemByID(int ID) {

		// Initialize Item
		Item item = null;

		try {
			// Prepare SQL Statement
			PreparedStatement statement = connection.prepareStatement(GET_ITEM_BY_ID);
			statement.setInt(1, ID);

			// Execute Statement & Handle Results
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				item = addItemFromResultSet(results);
			}

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Get Item by ID!");
		}

		// Return Item
		return item;
	}

	// Add Item to Database
	public boolean addItem(Item item) {

		// Initialize Flag & SQL Component
		boolean success = true;
		PreparedStatement statement = null;

		// Attempt to Add Item to Database
		try {
			// Prepare SQL Statement
			statement = connection.prepareStatement(ADD_ITEM);
			statement.setInt(1, item.getID());
			statement.setDouble(2, item.getPrice());
			statement.setString(3, item.getName());
			statement.setString(4, String.valueOf(item.getType()));
			statement.setInt(5, item.getQuantity());
			statement.setString(6, item.getBrandName());
			statement.setBoolean(7, item.isOrganic());
			statement.setDate(8, item.getExpirationDate() != null ? Date.valueOf(item.getExpirationDate()) : null);
			statement.setString(9, String.valueOf(item.getStatus()));
			statement.setString(10, item.getImageURL());

			// Execute Statement
			statement.executeUpdate();

		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR ItemDAO: Cannot Add Item!");
		}
		// Return Flag
		return success;
	}

	// Edit / Update Item in Database
	public boolean editItem(Item item) {

		// Initialize Flag
		boolean success = false;

		try {
			// Prepare SQL Statement
			PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM);
			statement.setDouble(1, item.getPrice());
			statement.setString(2, item.getName());
			statement.setInt(3, item.getQuantity());
			statement.setString(4, item.getBrandName());
			statement.setBoolean(5, item.isOrganic());
			statement.setDate(6, item.getExpirationDate() != null ? Date.valueOf(item.getExpirationDate()) : null);
			statement.setString(7, String.valueOf(item.getStatus()));
			statement.setInt(8, item.getID());

			// Execute Statement & Handle Results
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
				success = true;
			}

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Update Item!");
		}

		// Return Flag
		return success;
	}

	// Delete Item from Database
	public boolean deleteItem(Item item) {

		// Initialize Flag
		boolean success = false;

		try {
			// Prepare SQL Statement
			PreparedStatement statement = connection.prepareStatement(DELETE_ITEM);
			statement.setInt(1, item.getID());

			// Execute Statement & Handle Results
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				success = true;
			}

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Delete Item!");
		}

		// Return Flag
		return success;
	}

	// Get Item From Result Set
	private Item addItemFromResultSet(ResultSet results) {

		// Initialize Item
		Item item = null;

		try {
			// Extract Item Attributes from ResultSet
			int ID = results.getInt("ID");
			double price = results.getDouble("Price");
			Item.ItemType type = Item.ItemType.valueOf(results.getString("Type"));
			String name = results.getString("Name");
			int quantity = results.getInt("Quantity");
			String brandName = results.getString("BrandName");
			boolean isOrganic = results.getBoolean("isOrganic");
			Date expirationDateSQL = results.getDate("ExpirationDate");
			LocalDate expirationDate = (expirationDateSQL != null) ? expirationDateSQL.toLocalDate() : null;
			Item.ItemStatus status = Item.ItemStatus.valueOf(results.getString("Status"));
			String imageURL = results.getString("ImageURL");

			// Create a New Item Object
			item = new Item(ID, price, name, type, quantity, brandName, isOrganic, expirationDate, status, imageURL);

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Extract Items Attributes from ResultSet!");
		}

		// Return Item
		return item;
	}
}
