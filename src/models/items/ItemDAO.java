package models.items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import superstarsystem.DBManager;

public class ItemDAO {
	private final Connection connection;

	// SQL Queries
	private static final String GET_ALL_ITEMS = "SELECT * FROM Items";
	private static final String GET_ITEM_BY_ID = "SELECT * FROM Items WHERE ID = ?";
	private static final String ADD_ITEM = "INSERT INTO Items (ID, Price, Name, Type, Quantity, Status) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_ITEM = "UPDATE Items SET Quantity = ?, Price = ?, Status = ? WHERE ID = ?";
	private static final String DELETE_ITEM = "DELETE FROM Items WHERE ID = ?";

	// Default Class Constructor
	public ItemDAO(Connection connection) {
		this.connection = connection;
	}

	// Get All Items from Database
	public List<Item> getAllItems() {

		// Initialize Empty Product List
		List<Item> listOfItems = new ArrayList<>();

		// Initialize SQL Components
		PreparedStatement statement = null;
		ResultSet results = null;

		// Attempt to Get All Animals from Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(GET_ALL_ITEMS);

			// Execute Statement and Handle Results
			results = statement.executeQuery();
			while (results.next()) {
				Item item = addItemFromResultSet(results);
				listOfItems.add(item);
			}
		} catch (SQLException e) {
			System.out.println("ERROR ITEMDAO: Cannot Get Items! " + e);
		}

		// Return List of Products
		return listOfItems;
	}

	// Get Product By ID From Database
	public Item getItemByID(int ID) {
		// Initialize Empty Item & SQL Components
		Item item = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		// Attempt to Get Item from Database
		try {
			// Prepare Statement and Set Values
			statement = connection.prepareStatement(GET_ITEM_BY_ID);
			statement.setInt(1, ID);

			// Execute Statement and Handle Results
			results = statement.executeQuery();
			if (results.next()) {
				item = addItemFromResultSet(results);
			}
		} catch (SQLException e) {
			System.out.println("ERROR ITEMDAO: Cannot Get Item by ID! " + e);
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
			// Prepare Statement with the SQL Query
			statement = connection.prepareStatement(ADD_ITEM);
			statement.setInt(1, item.getID());
			statement.setDouble(2, item.getPrice());
			statement.setString(3, item.getName());
			statement.setString(4, item.getType());
			statement.setInt(5, item.getQuantity());
			statement.setString(6, item.getStatus());

			// Execute Statement
			statement.executeUpdate();
		} catch (SQLException e) {
			success = false;
			System.out.println("ERROR ITEMDAO: Cannot Add Item! " + e);
		}
		// Return Flag
		return success;
	}

	// Get Product From Result Set
	private Item addItemFromResultSet(ResultSet results) {

		// Initialize Empty Item
		Item item = null;

		// Attempt to Extract Item Attributes from ResultSet
		try {
			int ID = results.getInt("ID");
			double price = results.getDouble("Price");
			String name = results.getString("Name");
			String type = results.getString("Type");
			int quantity = results.getInt("Quantity");
			String status = results.getString("Status");

			// Create a New Item Object
			item = new Item(ID, price, name, type, quantity, status);
		} catch (SQLException e) {
			System.out.println("ERROR ITEMDAO: Cannot Extract Items Attributes from ResultSet! " + e);
		}

		// Return Item
		return item;
	}

}
