package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			String url = System.getenv("PROJECT2_URL");
			String username = System.getenv("JDBC_LOGIN");
			String pass = System.getenv("JDBC_PASSWORD");
			
			return DriverManager.getConnection(url, username, pass);
		} catch (SQLException e) {
			return null;
		}
	}
}
