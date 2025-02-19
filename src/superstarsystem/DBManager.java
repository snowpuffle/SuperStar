package superstarsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

public class DBManager {

	// SQL Information
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

		// Drop & Create Table
		dropTable(connection, "Items");
		createTable(connection, "create_items.sql", "Items");

	}

	// Load JDBC Driver Class
	private static void loadClassJDBCDriver() {
		try {
			// Load the MySQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: MySQL JDBC Driver NOT Found - Check JAR!");
			e.printStackTrace();
		}
	}

	// Connect to MySQL Database
	private static Connection connectToMySQL() {
		try {
			// Connect to MySQL
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			// System.out.println("SUCCESS: Established Connection with MySQL!");
			return connection;

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Establish Connection with MySQL!");
			e.printStackTrace();
		}
		return null;
	}

	// Connect to 'SUPERSTAR' Database
	private static Connection connectToDatabase(Connection connection) {

		// Check if the Database Exists.
		if (checkDatabaseExistence(connection)) {
			// System.out.println("INFO: Database '" + DATABASE_NAME.toUpperCase() + "'
			// Found!");
		} else {
			// Create the Database if it NOT Already Exist.
			// System.out.println("INFO: Database NOT Found! Creating Database...");
			connection = createDatabase(connection);
			// System.out.println("SUCCESS: Database Created.");
		}

		// Set the Active Database
		setActiveDatabase(connection);
		return connection;
	}

	// Create 'SUPERSTAR' Database
	private static Connection createDatabase(Connection connection) {
		try {
			// Execute Statement to Create Database
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE " + DATABASE_NAME);

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Create Database!");
			e.printStackTrace();
		}
		return connection;
	}

	// Check if 'SUPERSTAR' Database Exists
	private static boolean checkDatabaseExistence(Connection connection) {

		// Initialize Flag
		boolean exists = false;

		try {
			// Execute Statement to Query Database Existence
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + DATABASE_NAME + "'");

			// If the Result Set Contains ANY Rows, the Database Exists.
			exists = resultSet.next();

		} catch (SQLException e) {
			System.out.println("ERROR: Occured in Checking for Database Existence!");
			e.printStackTrace();
		}
		return exists;
	}

	// Create Table
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
			// System.out.println("SUCCESS: Table '" + tableName.toUpperCase() + "'
			// Created!");

		} catch (Exception e) {
			System.out.println("ERROR: Cannot Create '" + tableName.toUpperCase() + "' Table!");
			e.printStackTrace();
		}
	}

	// Drop Table if Exists
	private static void dropTable(Connection connection, String tableName) {
		try {
			// Execute Statement to Drop Table if Table Exists
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			// System.out.println("SUCCESS: '" + tableName + "' Dropped if Existed!");

		} catch (SQLException e) {
			System.out.println("ERROR: SQLException Occurred Dropping Table '" + tableName + "'!");
			e.printStackTrace();
		}
	}

	// Helper method to set the active database
	private static void setActiveDatabase(Connection connection) {
		try {
			// Execute Statement to Connect to Active Database
			Statement statement = connection.createStatement();
			statement.execute("USE " + DATABASE_NAME);
			System.out.println("SUCCESS: Connected to '" + DATABASE_NAME.toUpperCase() + "' Database!");

		} catch (SQLException e) {
			System.out.println("ERROR: Cannot Connect to '" + DATABASE_NAME.toUpperCase() + "' Database!");
			e.printStackTrace();
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
			System.out.println("ERROR: Cannot Close Connection to '" + DATABASE_NAME.toUpperCase() + "' Database!");
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