package com.hostmm.bct.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hostmm.bct.model.Book;
import com.hostmm.bct.model.BookDAO;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BookSwitchController implements Initializable {

	@FXML
	private TableColumn<Book, String> author;

	@FXML
	private TableColumn<Book, String> bookID;

	@FXML
	private TableColumn<Book, String> bookName;

	@FXML
	private TableColumn<Book, String> category;
	
	@FXML
	private TableColumn<Book, String> language;

	@FXML
	private TableColumn<Book, Integer> instock;

	@FXML
	private TableColumn<Book, Integer> pages;

	@FXML
	private TableColumn<Book, Double> price;

	@FXML
	private TableView<Book> tvBook;

	private Book book = Book.getBookInstance();
	private BookDAO bookDAO = new BookDAO();

	@FXML
	void processAdd(ActionEvent event) {
		showRegisterView();
	}

	@FXML
	void processDelete(ActionEvent event) {

	}

	void showRegisterView() {
		try {
			HBox root = (HBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/BookRegisterView.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void processEdit(ActionEvent event) {
		Book selectedBook = tvBook.getSelectionModel().getSelectedItem();
		if (selectedBook != null) {
			Book storedBook = this.bookDAO.getBook("bookID", selectedBook.getBookID());

			this.book.setBookID(storedBook.getBookID());
			this.book.setBookName(storedBook.getBookName());
			this.book.setAuthor(storedBook.getAuthor());
			this.book.setPages(storedBook.getPages());
			this.book.setLanguage(storedBook.getLanguage());
			this.book.setInstock(storedBook.getInstock());
			this.book.setCategory(storedBook.getCategory());
			this.book.setPrice(storedBook.getPrice());
			this.book.setDescription(storedBook.getDescription());
			this.book.setImageName(storedBook.getImageName());
			this.book.setBookHistory(storedBook.getBookHistory());
			this.book.setActive(storedBook.isActive());

			showRegisterView();
		}

	}

	@FXML
	void processRefresh(ActionEvent event) {
		showTable();
	}

	@FXML
	void processShowSoldOut(ActionEvent event) {

	}

	void showTable() {
		bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
		bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
		author.setCellValueFactory(new PropertyValueFactory<>("author"));
		pages.setCellValueFactory(new PropertyValueFactory<>("pages"));
		language.setCellValueFactory(new PropertyValueFactory<>("language"));
		instock.setCellValueFactory(new PropertyValueFactory<>("instock"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		ObservableList<Book> bookList = this.bookDAO.getBookList();
		tvBook.setItems(bookList);
//		tfSearch.textProperty().addListener(
//				(observable, oldValue, newValue) -> tvUser.setItems(filterList(userList, newValue.toLowerCase())));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showTable();
	}

}
