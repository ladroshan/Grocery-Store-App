package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * This Class is used exclusively for running INSERT Queries to the databse.
 * A lot of work has gone into abstracting the workings of this class. Keep the abstraction going if 
 * methods need to be added down the road.
 * 
 * @author Jacob Killpack
 * @version 1.5
 */
public class JDBCInsert {
	//The following final variables are the hard-coded values for creating a database connection
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/scangro";
	private static final String DB_USER = "app";
	private static final String DB_PASSWORD = "Th3Cak3IsALi3!";
	
	//This String is a tool of abstraction used to build different insert queries depending on how this class is called
	private static String Query;
	
	//This Vector is a collection of Strings compiled from the results of the database query
	//(It should be an empty vector before and after running queries to maintain high security)
	public static Vector<String> resultTable = new Vector<String>();
	
	/**
	 * This is the overloaded constructor for running INSERT queries into the inventory database.
	 * The psql query would look as follows:
	 * INSERT INTO inventory(producttype, provider, quantity, price) VALUES (product, provider, quantity, price);
	 * 
	 * @param table - The table will be overwritten to inventory for this constructor.
	 * @param product - This is the product you want to add.
	 * @param provider - This is the provider for the product that is being added.
	 * @param quantity - This is the quantity of the product being added.
	 * @param price - This is the price of the product being added.
	 */
	public JDBCInsert(String table, String product, String provider, String quantity, String price) {
		//Build the Query with user input then try pulling from database
		table = "inventory";
		Query = "INSERT INTO " + table + "(producttype, provider, quantity, price) VALUES ('"
				+ product + "', '" + provider + "', " + quantity + ", " 
				+ price + ")";
		try {
			insertRecordIntoDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Your attempt to Insert a record into "
					+ "the inventory database was not successful.", "Query Failure", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * This is the overloaded constructor for running INSERT queries into the users database.
	 * The psql query would look as follows:
	 * INSERT INTO users(username, password, is_admin) VALUES (user, pass, is_admin);
	 * 
	 * @param table - The table will be overwritten to users for this constructor.
	 * @param user - This is the user you are adding to the database.
	 * @param pass - This is the password for the user that you are adding.
	 * @param is_admin - This indicates whether the user being added is an admin or not.
	 */
	public JDBCInsert(String table, String user, String pass, String is_admin) {
		//Build the Query with user input then try pulling from database
		table = "users";
		Query = "INSERT INTO " + table + "(username, password, is_admin) VALUES ('"
				+ user + "', '" + pass + "', " + is_admin + ")";
		try {
			insertRecordIntoDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Your attempt to Insert a record into "
					+ "the users database was not successful.", "Query Failure", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * This method tries to create a connection, make a statement, execute a query, and generate a list of results
	 * 
	 * @throws SQLException
	 */
	private static void insertRecordIntoDbUserTable() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet results;
			//execute INSERT SQL statement
			results = statement.executeQuery(Query);
			results.next();			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	/**
	 * This method is used to create a connection to the database.
	 * 
	 * @return Connection - The connection that was created (if the connection failed, NULL will be returned)
	 */
	private static Connection getDBConnection() {
		
		Connection dbConnection = null;
		try {
			//Use the hard-coded DB_DRIVER to make the connection
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());			
		}
		
		try {
			//Use the hard-coded connection variables to make the connection
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
		return dbConnection;
	}
	
	/**
	 * This method is a getter for the resultTable Vector of Strings.
	 * The method is used by the Mainframe to get the data from the Query for processing.
	 * 
	 * @return resultTable - A Vector of Strings
	 */
	public static Vector<String> getList() {
		return resultTable;
	}
	
	public static void main(String [] args) {
		
		try {
			insertRecordIntoDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
