package superstarsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The DBManager Class is responsible for managing database operations for the
 * system. This class handles the following core functionalities:
 * 
 * 1. JDBC Driver Loading: Ensures the MySQL JDBC driver is loaded before
 * establishing a connection.
 * 
 * 2. Database Connection Management: Establishes a connection to the MySQL
 * database server. Uses predefined credentials (username & password) and a
 * connection URL.
 * 
 * 3. Database Setup: Checks if the database exists. If the database exists, it
 * drops and recreates it to ensure a clean slate. If the database does not
 * exist, it creates a new database.
 * 
 * This class is designed to serve as the foundation for interacting with the
 * database, ensuring all database-related tasks are centralized and easily
 * maintainable.
 **/

public class DBManager {
	private static String DATABASE_NAME = "superstar";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password!";

	private static DBManager instance;
	private Connection connection;

	// Default Class Constructor
	public DBManager() {
		try {
			// Try to Load the MySQL JDBC Driver
			loadJDBCDriver();
		} catch (ClassNotFoundException e) {
			handleMessage(2, "MySQL JDBC Driver not found.");
			// e.printStackTrace();
		}
		try {
			// Try to Establish Connection with Database
			connection = establishConnection();
			handleMessage(1, "Established Connection!");
			// Handle Successful Connection
			handleSuccessfulConnection(connection);
		} catch (SQLException e) {
			handleMessage(2, "Cannot Establish Connection!");
			// e.printStackTrace();
		}
	}

	// Load the MySQL JDBC Driver
	private static void loadJDBCDriver() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	// Establish a Connection to Database
	private static Connection establishConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

	// Drop/Recreate the Database if it exists. Otherwise, Create a New Database.
	private static void handleSuccessfulConnection(Connection connection) throws SQLException {
		// Check if the Database Already Exists.
		if (checkDatabaseExistence(connection)) {
			// Drop the Existing Database to Reset its State.
			handleMessage(3, "Database Already Exists. Dropping Database ...");
			dropDatabase(connection);
			handleMessage(1, "Database Dropped!");
		} else {
			// Create a New Database if it does not Exist.
			handleMessage(3, "Database Not Found! Creating Database ...");
			createDatabase(connection);
			handleMessage(1, "Database Created!");
		}
	}

	private static boolean checkDatabaseExistence(Connection connection) throws SQLException {
		String checkDatabaseSQL = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
				+ DATABASE_NAME + "'";
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(checkDatabaseSQL)) {
			return resultSet.next();
		}
	}

	// Create New Database
	private static void createDatabase(Connection connection) {
		String createDatabaseSQL = "CREATE DATABASE " + DATABASE_NAME;
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(createDatabaseSQL);
		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Create Database!");
		}
	}

	// Delete Database
	private static void dropDatabase(Connection connection) {
		String dropDatabaseSQL = "DROP DATABASE " + DATABASE_NAME;
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(dropDatabaseSQL);
		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Drop Database!");
		}
	}

	private static void handleMessage(int type, String message) {
		if (type == 1) {
			System.out.println("SUCCESS: " + message);
		} else if (type == 2) {
			System.out.println("ERROR: " + message);
		} else {
			System.out.println("INFO: " + message);
		}
	}
}