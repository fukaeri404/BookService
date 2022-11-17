package com.hostmm.bct.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hostmm.bct.database.DBConnection;
import com.hostmm.bct.utility.crypto.PasswordValidator;

public class LoginDAO {
	private Connection connection;
	private PreparedStatement pStmt;
	private ResultSet rs;

	static String loggedUserID;
	static boolean isSenior;

	public boolean isCredentialsValid(String username, String password, String role) throws SQLException {
		connection = DBConnection.getDBconnection();
		boolean signinOk = false;
		String storedPassword = null;

		try {
			pStmt = connection.prepareStatement("select username,password,userID,senior from " + role + " where username=?;");
			pStmt.setString(1, username);
			rs = pStmt.executeQuery();
			while (rs.next()) {
				storedPassword = rs.getString("password");
				loggedUserID = rs.getString("userID");
				isSenior = rs.getBoolean("senior");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (storedPassword != null)
			try {
				signinOk = PasswordValidator.validatePassword(password, storedPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return signinOk;
	}

	private void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getLoggedUserID() {
		return loggedUserID;
	}

	public static boolean getIsSenior() {
		return isSenior;
	}

}
