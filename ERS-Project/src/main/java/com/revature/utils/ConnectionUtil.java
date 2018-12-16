package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	// Force Java to load driver class
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {

		try {
			String url = System.getenv("PROJECT1_URL");
			String username = System.getenv("PROJECT1_LOGIN");
			String password = System.getenv("PROJECT1_PASSWORD");
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
