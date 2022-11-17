package com.hostmm.bct.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hostmm.bct.database.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookDAO {
	private Connection connection;
	private PreparedStatement pStmt;
	private Statement stmt;
	private ResultSet rs;

	public int createBook(Book book) {

		connection = DBConnection.getDBconnection();
		int rowEffected = 0;
		try {

			pStmt = connection.prepareStatement(
					"INSERT INTO `hospitaldb`.`book` (`bookID`, `bookName`, `author`, `pages`,`language`, `instock`, `category`, `price`, `description`, `imageName`, `bookHistory`, `isActive`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

			pStmt.setString(1, book.getBookID());
			pStmt.setString(2, book.getBookName());
			pStmt.setString(3, book.getAuthor());
			pStmt.setInt(4, book.getPages());
			pStmt.setString(5, book.getLanguage());
			pStmt.setInt(6, book.getInstock());
			pStmt.setString(7, book.getCategory());
			pStmt.setDouble(8, book.getPrice());
			pStmt.setString(9, book.getDescription());
			pStmt.setString(10, book.getImageName());
			pStmt.setString(11, book.getBookHistory());
			pStmt.setBoolean(12, book.isActive());

			rowEffected = pStmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;

	}

	public int updateBook(Book book) {

		int rowEffected = 0;
		connection = DBConnection.getDBconnection();
		try {
			pStmt = connection.prepareStatement(
					"UPDATE `hospitaldb`.`book` SET `bookName` = ?, `author` = ?, `pages` = ?, `language` = ?, `instock` = ?, `category` = ?, `price` = ?, `description` = ?, `imageName` = ?, `bookHistory` = ?, `isActive` = ? WHERE (`bookID` = ?);");
			pStmt.setString(1, book.getBookName());
			pStmt.setString(2, book.getAuthor());
			pStmt.setInt(3, book.getPages());
			pStmt.setString(4, book.getLanguage());
			pStmt.setInt(5, book.getInstock());
			pStmt.setString(6, book.getCategory());
			pStmt.setDouble(7, book.getPrice());
			pStmt.setString(8, book.getDescription());
			pStmt.setString(9, book.getImageName());
			pStmt.setString(10, book.getBookHistory());
			pStmt.setBoolean(11, book.isActive());
			pStmt.setString(12, book.getBookID());
			rowEffected = pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return rowEffected;

	}

	public ObservableList<Book> getBookList() {

		String sql = "select * from book where isActive = true;";
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		connection = DBConnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				bookList.add(new Book(rs.getString("bookID"), rs.getString("bookName"), rs.getString("author"),
						rs.getInt("pages"), rs.getString("language"), rs.getInt("instock"), rs.getString("category"),
						rs.getDouble("price"), rs.getString("description"), rs.getString("imageName"),
						rs.getString("bookHistory"), rs.getBoolean("isActive")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return bookList;

	}

	public Book getBook(String columnName, String value) {

		Book book = null;
		connection = DBConnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from book where " + columnName + " = '" + value + "';");

			while (rs.next()) {
				book = new Book(rs.getString("bookID"), rs.getString("bookName"), rs.getString("author"),
						rs.getInt("pages"), rs.getString("language"), rs.getInt("instock"), rs.getString("category"),
						rs.getDouble("price"), rs.getString("description"), rs.getString("imageName"),
						rs.getString("bookHistory"), rs.getBoolean("isActive"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return book;

	}

	public ObservableList<String> getAuthorList() {
		ObservableList<String> authorList = FXCollections.observableArrayList();
		connection = DBConnection.getDBconnection();
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select author from hospitaldb.book where isActive = true;");
			while (rs.next()) {

				String author = rs.getString("author");
				if (authorList.size() == 0)
					authorList.add(author);
				else
					for (int i = 0; i < authorList.size(); i++) {
						if (!authorList.get(i).equalsIgnoreCase(author))
							authorList.add(author);
					}

			}
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return authorList;

	}

	public int getBookCount() {
		int totalRows = 0;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select count(*) from book;");
			while (rs.next()) {
				totalRows += 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.closeDBconnection();
		}
		return totalRows;
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
}
