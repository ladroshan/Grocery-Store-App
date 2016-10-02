package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * This Class is used for running Queries to the database. 
 * A lot of work has gone into abstracting the workings of this class. Keep the abstraction going if 
 * methods need to be added down the road.
 * 
 * @author Jacob Killpack
 * @version 1.0
 */
public abstract class JDBC {
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
	 * This is the overloaded constructor that is called by the Mainframe to run the SELECT queries
	 * The psql query would look as follows:
	 * SELECT * FROM table WHERE where = test; 
	 * 
	 * @param table - This is the table in the database you want to query.
	 * @param where - This is the field that will be evaluated in the WHERE conditional.
	 * @param test - This is the value you are testing in the WHERE conditional.
	 */
	private QueryBuilder(String table, String where, String test) {
		//Build the Query with user input then try pulling from database 
		Query = "SELECT * FROM " + table + " WHERE " + where + " = " + test;
		try {
			selectRecordFromDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "The Username or Password that you have"
					+ " entered is incorrect!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * This method tries to create a connection, make a statement, execute a query, and generate a list of results
	 * 
	 * @throws SQLException
	 */
	private static void selectRecordFromDbUserTable() throws SQLException {
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
			
			//Send the ResultSet to a the makeList() method to add the data in it to a the resultTable Vector
			makeList(results);
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
	 * This method is used to compile a list of Strings from the ResultSet returned by the database query.
	 * The Vector of Strings will then be used in the Mainframe for data processing.
	 * 
	 * @param results - The ResultSet from the database query. 
	 */
	private static void makeList(ResultSet results) {
		try {
			//This counter is used to iterate through all elements of ResultSet
			int count = 1;
			
			//This object is necessary to get a count of the columns for an upper bound in the while loop
			ResultSetMetaData info = results.getMetaData();
			while (count <= info.getColumnCount()) {
				//Add results to resultTable Vector
				resultTable.add(results.getString(count));
				count++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
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
			selectRecordFromDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
