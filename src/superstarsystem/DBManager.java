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
		loadClassJDBCDriver();

		// Connect to the MySQL
		connection = connectToMySQL();

		// Connect to Database
		connection = connectToDatabase(connection);

		// Drop and Create Table
		dropTable(connection, "items".toUpperCase());
		createTable(connection, "create_items.sql", "Items".toUpperCase());
	}

	// Load JDBC Driver Class
	private static void loadClassJDBCDriver() {
		try {
			// Load the MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			handleMessage(2, "MySQL JDBC Driver NOT Found - Check JAR!");
			e.printStackTrace();
		}
	}

	// Establish Connection to MySQL
	private static Connection connectToMySQL() {
		try {
			// Connect to MySQL
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			// handleMessage(1, "Established Connection with MySQL!");
			return connection;

		} catch (SQLException e) {
			handleMessage(2, "Cannot Establish Connection with MySQL!");
			e.printStackTrace();
		}
		return null;
	}

	// Check if Database Exists. Otherwise, Create a New Database.
	private static Connection connectToDatabase(Connection connection) {

		// Check if the Database Exists or Not.
		if (checkDatabaseExistence(connection)) {
			// handleMessage(3, "Database '" + DATABASE_NAME.toUpperCase() + "' Found!");
		} else {
			// Create the Database if it Does NOT Exist.
			handleMessage(3, "Database NOT Found! Creating Database...");
			connection = createDatabase(connection);
			handleMessage(1, "Database Created!");
		}

		// Set the Active Database
		setActiveDatabase(connection);
		return connection;
	}

	// Create New Database
	private static Connection createDatabase(Connection connection) {
		try {
			// Execute Statement to Create Database
			String createDatabaseSQL = "CREATE DATABASE " + DATABASE_NAME;
			Statement statement = connection.createStatement();
			statement.executeUpdate(createDatabaseSQL);

		} catch (SQLException e) {
			handleMessage(2, "Cannot Create Database!");
			e.printStackTrace();
		}
		return connection;
	}

	// Drop Table if Exists
	private static void dropTable(Connection connection, String tableName) {
		try {
			// Execute Statement to Drop Table if Table Exists
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			// handleMessage(1, "'" + tableName + "' Dropped if Existed!");

		} catch (SQLException e) {
			handleMessage(2, "SQLException Occurred Dropping Table '" + tableName + "'!");
			e.printStackTrace();
		}
	}

	// Initialize and Set Table Database
	private static void createTable(Connection connection, String fileName, String tableName) {
		try {
			// Initialize File Location
			String fileLocation = System.getProperty("user.dir") + "\\resources\\scripts\\" + fileName;

			// Read the SQL Script to Create SQL Table
			File file = new File(fileLocation);
			InputStream inputStream = new FileInputStream(file);
			String SQL = new String(inputStream.readAllBytes());
			inputStream.close();

			// Execute Statement to Create & Initialize Table
			Statement statement = connection.createStatement();
			statement.executeUpdate(SQL);
			handleMessage(1, "Table '" + tableName + "' Set!");

		} catch (SQLException e) {
			handleMessage(2, "SQLException Occurred Initializing Table '" + tableName + "'!");
			e.printStackTrace();

		} catch (IOException e) {
			handleMessage(2, "IOException Occurred Initializing Table '" + tableName + "'!");
			e.printStackTrace();
		}
	}

	// Check if Database Exists
	private static boolean checkDatabaseExistence(Connection connection) {
		// Variable to Store if the Database Exists
		boolean exists = false;

		try {
			// Execute Statement to Query Database Existence
			String checkDatabaseSQL = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '"
					+ DATABASE_NAME + "'";
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

	// Helper method to set the active database
	private static void setActiveDatabase(Connection connection) {
		try {
			// Execute Statement to Connect to Active Database
			handleMessage(3, "Connecting to Database ...");
			Statement statement = connection.createStatement();
			statement.execute("USE " + DATABASE_NAME);
			handleMessage(1, "Connected to '" + DATABASE_NAME.toUpperCase() + "' Database!");

		} catch (SQLException e) {
			handleMessage(2, "SQLException Occurred Setting Active Database!");
			e.printStackTrace();
		}
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