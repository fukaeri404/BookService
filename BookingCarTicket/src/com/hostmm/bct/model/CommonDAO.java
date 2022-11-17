package com.hostmm.bct.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.hostmm.bct.database.DBConnection;

public class CommonDAO{
	private Statement stmt;
	private ResultSet rs;
	private Connection connection = DBConnection.getDBconnection();

	public int getRowCount(String tableName) {
		int rowNumbers = 0;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select count(*) from " + tableName + ";");
			while (rs.next()) {
				rowNumbers = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeDBconnection();
		}
		return rowNumbers;
	}
}
