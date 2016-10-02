package database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * THIS IS JUST A TEST CLASS THAT WAS USED TO TEST THE DATABASE CONNECTION. THIS CLASS WILL BE REMOVED 
 * IN THE RELEASE OF THE APPLICATION.
 * 
 * @author mkyong
 *
 */
public class JDBC {

	public static void main(String[] argv) {

		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {
			//The DB_DRIVER is being passed as a parameter of forName() here
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {
			//This was the most important thing from this test that I got. This connection shows how we connect
			//to the database. 
			//The DB_CONNECTION is 'jdbc:postgresql://<localhostIPAddress>:<portNumber>/<databaseName>
			//The DB_USER is the user that you have assigned in postgres (You could use the default "superuser" 
			//postgres, but I felt like that was insecure. So I created a new user and I am giving it permissions
			//as I go)
			//The DB_PASSWORD again is the password you assign to the user you created in postgres
			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/scangro", "app",
					"Th3Cak3IsALi3!");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}