package edu.mum.cs.uis.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DB_URL = "jdbc:derby:database\\UsedItemsDB;user=admin;password=admin";
	
	private Connection connection;
	
	private static DBConnection instance = new DBConnection();
	
	private DBConnection() {
		try {
			// Connect to DB
			System.out.println("Loading the Derby jdbc driver...");
			Class.forName(DB_DRIVER).newInstance();
		
			System.out.println("Connecting to the UsedItemsDB database on JavaDB server...");
			connection = DriverManager.getConnection(DB_URL);
			System.out.println("Successfully made the connection to SRSDB.");
		} catch(Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}
	
	public static DBConnection getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static void main(String[] args) {
		DBConnection connection = DBConnection.getInstance();
	}

}
