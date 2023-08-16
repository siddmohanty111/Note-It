package application;

import java.sql.*;

public class SqliteConnection {
	public static Connection Connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:JournalAppDB.db");
			return conn;
		} catch (Exception e){
			System.out.println(e);
			return null;
		}
	}
	
	public static void closeConnection(Connection conn) {
	    try {
	        if (conn != null) {
	            conn.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}