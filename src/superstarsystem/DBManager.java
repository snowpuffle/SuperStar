package superstarsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DBManager {
	private static String DATABASE_NAME = "superstar";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password!";

	private static DBManager instance;
	private Connection connection;

	// Default Class Constructor
	public DBManager() {
		// Load the MySQL JDBC Driver
		loadJDBCDriver();

		// Establish Connection with Database
		connection = establishConnection();

		try {
			// Handle Successful Connection
			connection = handleSuccessfulConnection(connection);

			// Reset Database
			resetDatabase(connection.createStatement(), "create_items.sql", "Items Re/Generated!");

		} catch (IOException e) {
			handleMessage(2, "IOException Occurred during Database Handling!");
			e.printStackTrace();
		} catch (SQLException e) {
			handleMessage(2, "SQLException Occurred during Database Connection Handling!");
			e.printStackTrace();
		}
	}

	// Load the MySQL JDBC Driver
	private static void loadJDBCDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			handleMessage(2, "MySQL JDBC Driver NOT Found. Check JAR!");
			// e.printStackTrace();
		}
	}

	// Establish a Connection to Database
	private static Connection establishConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			handleMessage(1, "Established Connection with MySQL!");
		} catch (SQLException e) {
			handleMessage(2, "Cannot Establish Connection");
			e.printStackTrace();
		}
		return connection;
	}

	// Check if Database Exists. Otherwise, Create a New Database.
	private static Connection handleSuccessfulConnection(Connection connection) throws SQLException {
		// Check if the Database Exists or Not.
		if (checkDatabaseExistence(connection)) {
			// handleMessage(3, "Database Found! Resetting Database...");
			dropAndCreateDatabase(connection);
		} else {
			// Create the Database if it Does NOT Exist.
			// handleMessage(3, "Database NOT Found! Creating Database...");
			connection = createDatabase(connection);
			// handleMessage(1, "Database Created!");
		}

		// Set the Active Database and Reset
		setActiveDatabase(connection);
		return connection;
	}

	// Drops and recreates the database
	private static void dropAndCreateDatabase(Connection connection) throws SQLException {
		connection = dropDatabase(connection); // Drop the existing database
		// handleMessage(1, "Database Dropped!");
		connection = createDatabase(connection); // Create a new one
		// handleMessage(1, "Database Created!");
	}

	// Sets the active database to the correct one
	private static void setActiveDatabase(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute("USE " + DATABASE_NAME);
	}

	// Create New Database
	private static Connection createDatabase(Connection connection) {
		String createDatabaseSQL = "CREATE DATABASE " + DATABASE_NAME;
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(createDatabaseSQL);
		} catch (SQLException e) {
			handleMessage(2, "Cannot Create Database!");
			e.printStackTrace();
		}
		return connection;
	}

	// Delete Database
	private static Connection dropDatabase(Connection connection) {
		String dropDatabaseSQL = "DROP DATABASE " + DATABASE_NAME;
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate(dropDatabaseSQL);
		} catch (SQLException e) {
			handleMessage(2, "Cannot Drop Database!");
		}
		return connection;
	}

	// Reset Database
	public static void resetDatabase(Statement statement, String fileName, String successMessage)
			throws IOException, SQLException {
		// Initialize File Location
		String fileLocation = System.getProperty("user.dir") + "\\resources\\scripts\\" + fileName;

		// Read the SQL Script to Create SQL Table
		File file = new File(fileLocation);
		InputStream inputStream = new FileInputStream(file);
		String SQL = new String(inputStream.readAllBytes());
		inputStream.close();

		// Execute the SQL Script
		statement.executeUpdate(SQL);
		handleMessage(1, successMessage);
	}

	// Check if Database Exists
	private static boolean checkDatabaseExistence(Connection connection) {
		// SQL Query to check if the Database Schema Exists.
		String checkDatabaseSQL = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
				+ DATABASE_NAME + "'";

		// Variable to Store if the Database Exists
		boolean exists = false;

		try {
			// Create a Statement Object and Execute the Query
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(checkDatabaseSQL);

			// If the Result Set Contains ANY Rows, the Database Exists.
			exists = resultSet.next();
		} catch (SQLException e) {
			handleMessage(2, "SQL Query Error in Checking for Database Existence.");
			e.printStackTrace();
		}
		return exists;
	}

	// Handle Messages Given Message Type
	private static void handleMessage(int type, String message) {
		if (type == 1) {
			System.out.println("SUCCESS: " + message);
		} else if (type == 2) {
			System.out.println("ERROR: " + message);
		} else {
			System.out.println("INFO: " + message);
		}
	}

	// Close Database Connection
	public void closeConnection() {
		// Attempt to Close Database Connection
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			handleMessage(2, "Issues Encountered when Closing Database Connection!");
		}
	}

	// Get Database Instance
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	// Get Database Connection
	public Connection getConnection() {
		return connection;
	}
}