package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


/**
 * This Class is used exclusively for running SELECT Queries to the database. 
 * A lot of work has gone into abstracting the workings of this class. Keep the abstraction going if 
 * methods need to be added down the road.
 * 
 * @author Jacob Killpack
 * @version 1.2
 */
public class JDBCUpdate {
	//The following final variables are the hard-coded values for creating a database connection
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/scangro";
	private static final String DB_USER = "app";
	private static final String DB_PASSWORD = "Th3Cak3IsALi3!";
	
	//This String is a tool of abstraction used to build different select queries depending on how this class is called
	private static String Query;
	
	//This Vector is a collection of Strings compiled from the results of the database query
	//(It should be an empty vector before and after running queries to maintain high security)
	public static Vector<String> resultTable = new Vector<String>();
	
	/**
	 * This is the overloaded constructor for running UPDATE queries into the inventory database.
	 * The psql query would look as follows:
	 * UPDATE inventory SET producttype = product, provider = provider, quantity = quantity, price = price WHERE where = id);
	 * 
	 * @param table - This is the table in the database you want to query.
	 * @param product - This is the product field value.
	 * @param provider - This is the provider field value.
	 * @param quantity - This is the quantity field that will be updated.
	 * @param price - This is the price field for the object.
	 * @param where - This is the field that will be evaluated in the WHERE conditional.
	 * @param id - This is the id value that will be used to qualify the Query.
	 */
	public JDBCUpdate(String table, String product, String provider, String quantity, String price, String where, String id) {
		//Build the Query with user input then try pulling from database
		table = "inventory";
		where = "id";
		Query = "UPDATE " + table + " SET producttype = '" + product + "', provider = '" + provider + "', quantity = " + quantity + ", price = " + price
				+ " WHERE " + where + " = " + id;
		try {
			updateRecordFromDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * This is the overloaded constructor for running UPDATE queries into the users database.
	 * The psql query would look as follows:
	 * UPDATE inventory SET username = user, password = pass, is_admin = is_admin WHERE where = id);
	 * 
	 * @param table - This is the table in the database you want to query.
	 * @param user - This is the user field that will be updated.
	 * @param pass - This is the password field value that will updated.
	 * @param is_admin - This is the boolean field that will be updated.
	 * @param where - This is the field that will be evaluated in the WHERE conditional.
	 * @param id - This is the id value that will be used to qualify the Query.
	 */
	public JDBCUpdate(String table, String user, String pass, String is_admin, String where, String id) {
		//Build the Query with user input then try pulling from database
		table = "users";
		where = "id";
		Query = "UPDATE " + table + " SET username = '" + user + "', password = '" + pass + "', is_admin = " + is_admin
				+ " WHERE " + where + " = " + id;
		try {
			updateRecordFromDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * This method tries to create a connection, make a statement, execute a query, and generate a list of results
	 * 
	 * @throws SQLException
	 */
	private static void updateRecordFromDbUserTable() throws SQLException {
		//Create and initialize variables
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(Query);
			
			//The ResultSet is used to hold results of the query
			ResultSet results;
			
			//execute SELECT SQL Query
			results = statement.executeQuery(Query);
			
			//This needs to be done to move to the first row of data in the ResultSet
			results.next();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		//If the try goes through, then make sure to close the Statement and Connection before closing	
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
	
	/**
	 * Main method
	 * This method won't be run.
	 * 
	 * @param args - None
	 */
	public static void main(String [] args) {
		try {
			updateRecordFromDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}