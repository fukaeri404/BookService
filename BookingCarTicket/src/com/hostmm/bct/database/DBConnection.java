package com.hostmm.bct.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getDBconnection() {
		String url = "jdbc:mysql://localhost:3306/hospitaldb?useSSL=false";
		String user = "root";
		String password = "1234";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}

	public static void closeDBconnection() {
		if (getDBconnection() != null)
			try {
				getDBconnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
