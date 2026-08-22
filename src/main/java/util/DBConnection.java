package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL =
            System.getenv("DB_URL");;
	private static final String USER =
            System.getenv("DB_USER");;
	private static final String PASSWORD =
            System.getenv("DB_PASSWORD");
	
	// This method gives DB connection 
	public static Connection getConnection() {

        Connection connection = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
