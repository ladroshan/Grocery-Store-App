package database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * This Class is used exclusively for running INSERT Queries to the databse.
 * A lot of work has gone into abstracting the workings of this class. Keep the abstraction going if 
 * methods need to be added down the road.
 * 
 * @author Jacob Killpack
 * @version 1.2
 */
public class JDBCInsert {
	//The following final variables are the hard-coded values for creating a database connection
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/scangro";
	private static final String DB_USER = "app";
	private static final String DB_PASSWORD = "Th3Cak3IsALi3!";
	
	//This String is a tool of abstraction used to build different insert queries depending on how this class is called
	//private static String Query;
	
	//This Vector is a collection of Strings compiled from the results of the database query
	//(It should be an empty vector before and after running queries to maintain high security)
	public static Vector<String> resultTable = new Vector<String>();
	
	//public JDBCInsert(String table, )
	
	private static void insertRecordIntoDbUserTable() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String insertTableSQL = "INSERT INTO users"
				+ "(id, username, password, is_admin) " + "VALUES" 
				+ "(3, 'three.days.grace', 'TryNot2!', true)";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println("Create Statement Success");
			System.out.println(insertTableSQL);
			
			//execute insert SQL statement
			statement.executeQuery(insertTableSQL);
			System.out.println("Record was inserted Successfully into users table!");
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
	
	private static Connection getDBConnection() {
		
		Connection dbConnection = null;
		
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());			
		}
		
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return dbConnection;
	}
	
	//WORK NEEDED - Add a makeList() method and a getList() getter method. See JDBCSelect for examples
	
	public static void main(String [] args) {
		
		try {
			insertRecordIntoDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
