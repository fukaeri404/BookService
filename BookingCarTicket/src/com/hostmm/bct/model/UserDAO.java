package com.hostmm.bct.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.hostmm.bct.database.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {

	private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;
	private Connection connection;

	public int createUser(User user, String tableName) {
		connection = DBConnection.getDBconnection();
		int rowEffected = 0;
		try {
			if (tableName.equalsIgnoreCase("admin"))
				pStmt = connection.prepareStatement(
						"INSERT INTO `hospitaldb`.`admin` (`userID`, `username`, `password`, `firstName`, `lastName`, `age`, `birthDate`, `gender`, `nrc`, `address`, `phone`, `role`, `imageName`, `isActive`, `accountHistory`, `status`, `employDate`, `resignDate`, `salary`, `isSenior`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			else
				pStmt = connection.prepareStatement(
						"INSERT INTO `hospitaldb`.`customer` (`userID`, `username`, `password`, `firstName`, `lastName`, `age`, `birthDate`, `gender`, `nrc`, `address`, `phone`, `role`, `imageName`, `isActive`, `accountHistory`,`occupation`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

			pStmt.setString(1, user.getUserID());
			pStmt.setString(2, user.getUsername());
			pStmt.setString(3, user.getPassword());
			pStmt.setString(4, user.getFirstName());
			pStmt.setString(5, user.getLastName());
			pStmt.setInt(6, user.getAge());
			Date birthDate = Date.valueOf(user.getBirthDate());
			pStmt.setDate(7, birthDate);
			pStmt.setString(8, user.getGender());
			pStmt.setString(9, user.getNrc());
			pStmt.setString(10, user.getAddress());
			pStmt.setString(11, user.getPhone());
			pStmt.setString(12, user.getRole());
			pStmt.setString(13, user.getImageName());
			pStmt.setBoolean(14, user.isActive());
			pStmt.setString(15, user.getAccountHistory());
			if (tableName.equalsIgnoreCase("admin")) {
				pStmt.setString(16, user.getStatus());
				Date employDate = Date.valueOf(user.getEmployDate());
				pStmt.setDate(17, employDate);
				Date resignDate = Date.valueOf(user.getResignDate());
				pStmt.setDate(18, resignDate);
				pStmt.setDouble(19, user.getSalary());
				pStmt.setBoolean(20, user.getSenior());
			} else
				pStmt.setString(16, user.getOccupation());

			rowEffected = pStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;

	}

	public int updateUser(User user, String tableName) {
		int rowEffected = 0;
		connection = DBConnection.getDBconnection();
		try {
			if (tableName.equalsIgnoreCase("admin"))
				pStmt = connection.prepareStatement(
						"UPDATE `hospitaldb`.`admin` SET `username` = ?, `password` = ?, `firstName` = ?, `lastName` = ?, `age` = ?, `birthDate` = ?, `gender` = ?, `nrc` = ?, `address` = ?, `phone` = ?, `role` = ?, `imageName` = ?, `isActive` = ?, `accountHistory` = ?, `status` = ?, `employDate` = ?, `resignDate` = ?, `salary` = ?, `senior` = ? WHERE (`userID` = ?);");
			else
				pStmt = connection.prepareStatement(
						"UPDATE `hospitaldb`.`customer` SET `username` = ?, `password` = ?, `firstName` = ?, `lastName` = ?, `age` = ?, `birthDate` = ?, `gender` = ?, `nrc` = ?, `address` = ?, `phone` = ?, `role` = ?, `imageName` = ?, `isActive` = ?, `accountHistory` = ?, `occupation` = ? WHERE (`userID` = ?);");

			pStmt.setString(1, user.getUsername());
			pStmt.setString(2, user.getPassword());
			pStmt.setString(3, user.getFirstName());
			pStmt.setString(4, user.getLastName());
			pStmt.setInt(5, user.getAge());
			Date birthDate = Date.valueOf(user.getBirthDate());
			pStmt.setDate(6, birthDate);
			pStmt.setString(7, user.getGender());
			pStmt.setString(8, user.getNrc());
			pStmt.setString(9, user.getAddress());
			pStmt.setString(10, user.getPhone());
			pStmt.setString(11, user.getRole());
			pStmt.setString(12, user.getImageName());
			pStmt.setBoolean(13, user.isActive());
			pStmt.setString(14, user.getAccountHistory());
			if (tableName.equalsIgnoreCase("admin")) {
				pStmt.setString(15, user.getStatus());
				Date employDate = Date.valueOf(user.getEmployDate());
				pStmt.setDate(16, employDate);
				Date resignDate = Date.valueOf(user.getResignDate());
				pStmt.setDate(17, resignDate);
				pStmt.setDouble(18, user.getSalary());
				pStmt.setBoolean(19, user.getSenior());
				pStmt.setString(20, user.getUserID());
			} else {
				pStmt.setString(15, user.getOccupation());
				pStmt.setString(16, user.getUserID());
			}

			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;
	}

	public ObservableList<User> getUserList(String tableName) {
		String sql = "select * from " + tableName + " where isActive = true;";
		ObservableList<User> userList = FXCollections.observableArrayList();
		connection = DBConnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			if (tableName.equalsIgnoreCase("admin")) {
				while (rs.next()) {
					userList.add(new User(rs.getString("userID"), rs.getString("username"), rs.getString("password"),
							rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"),
							rs.getDate("birthDate").toLocalDate(), rs.getString("gender"), rs.getString("nrc"),
							rs.getString("address"), rs.getString("phone"), rs.getString("role"),
							rs.getString("imageName"), rs.getBoolean("isActive"), rs.getString("accountHistory"),
							rs.getString("status"), rs.getDate("employDate").toLocalDate(),
							rs.getDate("resignDate").toLocalDate(), rs.getDouble("salary"), rs.getBoolean("senior")));

				}
			} else
				while (rs.next()) {
					userList.add(new User(rs.getString("userID"), rs.getString("username"), rs.getString("password"),
							rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"),
							rs.getDate("birthDate").toLocalDate(), rs.getString("gender"), rs.getString("nrc"),
							rs.getString("address"), rs.getString("phone"), rs.getString("role"),
							rs.getString("imageName"), rs.getBoolean("isActive"), rs.getString("accountHistory"),
							rs.getString("occupation")));
				}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return userList;

	}

	public User getUser(String columnName, String value, String role) {
		User user = null;
		connection = DBConnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from " + role + " where " + columnName + " = '" + value + "';");

			if (role.equalsIgnoreCase("Admin"))
				while (rs.next()) {
					user = new User(rs.getString("userID"), rs.getString("username"), rs.getString("password"),
							rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"),
							rs.getDate("birthDate").toLocalDate(), rs.getString("gender"), rs.getString("nrc"),
							rs.getString("address"), rs.getString("phone"), rs.getString("role"),
							rs.getString("imageName"), rs.getBoolean("isActive"), rs.getString("accountHistory"),
							rs.getString("status"), rs.getDate("employDate").toLocalDate(),
							rs.getDate("resignDate").toLocalDate(), rs.getDouble("salary"), rs.getBoolean("senior"));
				}
			else
				while (rs.next()) {
					user = new User(rs.getString("userID"), rs.getString("username"), rs.getString("password"),
							rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"),
							rs.getDate("birthDate").toLocalDate(), rs.getString("gender"), rs.getString("nrc"),
							rs.getString("address"), rs.getString("phone"), rs.getString("role"),
							rs.getString("imageName"), rs.getBoolean("isActive"), rs.getString("accountHistory"),
							rs.getString("occupation"));
				}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user;
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

	public int deleteById(User user) {
		int rowEffected = 0;
		connection = DBConnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement("UPDATE `" + user.getRole().toLowerCase()
					+ "` SET isActive = ?,accountHistory = ? where userID = ?; ");

			pStmt.setBoolean(1, false);
			pStmt.setString(2,
					user.getAccountHistory() + ",deletedBy-" + LoginDAO.getLoggedUserID() + "@" + LocalDate.now());
			pStmt.setString(3, user.getUserID());
			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowEffected;
	}

}
