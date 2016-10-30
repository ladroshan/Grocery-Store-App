package database;

import main.Item;
import main.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * This Class is used exclusively for running SELECT Queries to the database. 
 * A lot of work has gone into abstracting the workings of this class. Keep the abstraction going if 
 * methods need to be added down the road.
 * 
 * @author Jacob Killpack
 * @version 1.10
 */
public class JDBCSelect {
	//The following final variables are the hard-coded values for creating a database connection
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/scangro";
	private static final String DB_USER = "app";
	private static final String DB_PASSWORD = "Th3Cak3IsALi3!";
	
	//This String is a tool of abstraction used to build different select queries depending on how this class is called
	private static String Query, Qcount;
	private static boolean dump = false;
	
	//This Vector is a collection of Strings compiled from the results of the database query
	//(It should be an empty vector before and after running queries to maintain high security)
	public static Vector<String> resultTable = new Vector<String>();
	private static List<Item> DaUdderInvList = new ArrayList<Item>();
	private static List<User> DaUdderUsrList = new ArrayList<User>();
	
	/**
	 * This is the overloaded constructor that is called by the Mainframe to run the SELECT queries
	 * The psql query would look as follows:
	 * SELECT * FROM table WHERE where = test; 
	 * 
	 * @param table - This is the table in the database you want to query.
	 * @param where - This is the field that will be evaluated in the WHERE conditional.
	 * @param test - This is the value you are testing in the WHERE conditional.
	 */
	public JDBCSelect(String table, String where, String test) {
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
	 * This is another overloaded constructor for pulling all records from the database.
	 * The psql query would look as follows:
	 * SELECT * FROM table;
	 * 
	 * @param table - The table from which you want all the records.
	 */
	public JDBCSelect(String table) {
			dump = true;
			Query = "SELECT * FROM " + table;
			Qcount = table;
			try {
				if(table.equals("inventory")){
					selectRecordFromDbUserTable();		
				} 
				else if (table.equals("users")){
					selectRecordFromDbUserTable();
				}
				else {
					JOptionPane.showMessageDialog(null, "There was an error in the Select Dump", "Database Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "The Username or Password that you have"
						+ " entered is incorrect!", "Password Error", 
						JOptionPane.ERROR_MESSAGE);
			}
			dump = false;
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
			int count = 1, prejudice = 0;
			
			System.out.println(Query);
			if (dump) {
				ResultSet r = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM " + Qcount);
				System.out.println(prejudice);
				r.next();
				count = r.getInt("rowcount");
				System.out.println(count);
				r.close();				
			}
			//The ResultSet is used to hold results of the query
			ResultSet results;
			
			//execute SELECT SQL Query
			results = statement.executeQuery(Query);
			ResultSetMetaData info = results.getMetaData();
			prejudice = info.getColumnCount();
			//This needs to be done to move to the first row of data in the ResultSet
			results.next();			
			//Send the ResultSet to a the makeList() method to add the data in it to a the resultTable Vector
			if (dump) {
				if(prejudice == 4) {
					makeDaUsrList(results, count);
				}
				else if (prejudice == 5) {
					makeDaInvList(results, count);
				}
				else {
					JOptionPane.showMessageDialog(null, "There was an error in the differentiating between"
							+ "whether this dump is for the User or Inventory databases.", "Database Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				makeList(results);
			}
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
			ResultSetMetaData info = results.getMetaData();
			int count = 1;
			while(count <= info.getColumnCount()) {
				resultTable.addElement(results.getString(count));
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	private static void makeDaInvList(ResultSet results, int max) {
		try {
			//This object is necessary to get a count of the columns for an upper bound in the while loop
				int itemNumber;
				String itemName;
				String aprovider;
				int quantity;
				double acost;
				String edit;
				for (int i = 0; i < max; i++){
					itemNumber = results.getInt(1);
					itemName = results.getString(2).trim();
					aprovider = results.getString(3).trim();
					quantity = results.getInt(4);
					edit = results.getString(5);
					edit = edit.substring(1);
					char[] removeCommas = edit.toCharArray();
					for (int j = 0; j < edit.length(); j++) {
						if (removeCommas[j] == ',') {
							edit = edit.substring(0, (j)) + edit.substring(j + 1);
							removeCommas = edit.toCharArray();
						}
					}
					acost = Double.parseDouble(edit);
					Item test = new Item(itemNumber, itemName, aprovider, quantity, acost);
					DaUdderInvList.add(test);
					results.next();
				}
			} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void makeDaUsrList(ResultSet results, int max) {
		try {
				int anId;
				String aUserName;
				String aPassword;
				boolean isAAdmin;
				for (int i = 0; i < max; i++){
					anId = results.getInt(1);
					aUserName = results.getString(2).trim();
					aPassword = results.getString(3).trim();
					isAAdmin = results.getBoolean(4);
					User test = new User(anId, aUserName, aPassword, isAAdmin);
					DaUdderUsrList.add(test);
					results.next();
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
	
	public static List<Item> getDaUdderInvList() {
		return DaUdderInvList;
	}
	
	public static List<User> getDaUdderUsrList() {
		return DaUdderUsrList;
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