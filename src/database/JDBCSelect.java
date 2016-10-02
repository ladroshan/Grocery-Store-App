package database;

/**
 * @author Jacob Killpack
 *
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

public class JDBCSelect {
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/scangro";
	private static final String DB_USER = "app";
	private static final String DB_PASSWORD = "Th3Cak3IsALi3!";
	private static String Query;
	public static Vector<String> resultTable = new Vector<String>();
	
	public JDBCSelect(String table, String where, String test) {
		Query = "SELECT * FROM " + table + " WHERE " + where + " = " + test;
		try {
			insertRecordIntoDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "The Username or Password that you have"
					+ " entered is incorrect!", "Password Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void insertRecordIntoDbUserTable() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			ResultSet results;
			System.out.println(Query);
			//execute insert SQL statement
			results = statement.executeQuery(Query);
			results.next();
			makeList(results);
			String testUser = results.getString(1);
			System.out.println(testUser);
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
	
	private static void makeList(ResultSet results) {
		try {
			int count = 1;
			ResultSetMetaData info = results.getMetaData();
			while (count <= info.getColumnCount()) {
				resultTable.add(results.getString(count));
				System.out.println("Added " + results.getString(count));
				count++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Vector<String> getList() {
		return resultTable;
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
	
	public static void main(String [] args) {
		try {
			insertRecordIntoDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}