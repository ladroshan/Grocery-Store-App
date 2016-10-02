package database;

/**
 * @author Jacob Killpack
 *
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCInsert {
	
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/scangro";
	private static final String DB_USER = "app";
	private static final String DB_PASSWORD = "Th3Cak3IsALi3!";
	
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
			System.out.println("DBConnection -> DBDRIVER Success");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());			
		}
		
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			System.out.println("DBConnection Success");
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
