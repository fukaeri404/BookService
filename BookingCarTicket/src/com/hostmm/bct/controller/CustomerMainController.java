package com.hostmm.bct.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.hostmm.bct.model.Book;
import com.hostmm.bct.model.BookDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerMainController implements Initializable {

	@FXML
	private JFXHamburger Hamburger;

	@FXML
	private JFXComboBox<String> cobAuthor;

	@FXML
	private JFXComboBox<String> cobCategory;

	@FXML
	private JFXComboBox<String> cobLanguage;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private StackPane stackPane;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private FlowPane flowPane;

	private BookDAO bookDAO = new BookDAO();

	@FXML
	void processSignOut(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> language = FXCollections.observableArrayList("English", "Myanmar");
		ObservableList<String> author = FXCollections.observableArrayList(bookDAO.getAuthorList());
		ObservableList<String> category = FXCollections.observableArrayList("Action", "Sci-Fi", "Sport", "Fantasy",
				"Horror");

		cobCategory.setItems(category);
		cobLanguage.setItems(language);
		cobAuthor.setItems(author);
		System.out.println(bookDAO.getAuthorList());

		initDrawer();
		addCard();

	}

	void addCard() {
		ObservableList<Book> bookList = bookDAO.getBookList();
		for (Book book : bookList) {
			try {
				VBox card = (VBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/BookCard.fxml"));

				for (Node node : card.getChildren()) {
					if (node.getAccessibleText() != null) {
						switch (node.getAccessibleText()) {
						case "BookName":
							((Label) node).setText(book.getBookName());
							break;
						case "image":
							((ImageView) node).setImage(new Image(getClass()
									.getResourceAsStream("/com/hostmm/bct/image/book/" + book.getImageName())));
							break;
						case "AuthorName":
							((Label) node).setText(book.getAuthor());
							break;
						case "Price":
							((Label) node).setText(String.valueOf(book.getPrice()));
							break;
						default:
							break;
						}
					}
				}

				card.setOnMouseClicked((e) -> {
					System.out.println(book.getBookID());
				});

				flowPane.getChildren().add(card);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void initDrawer() {

		try {
			VBox toolbar = FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/Toolbar.fxml"));
			drawer.setSidePane(toolbar);
			drawer.setDefaultDrawerSize(200);

			for (Node node : toolbar.getChildren()) {
				if (node.getAccessibleText() != null) {
					node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
						switch (node.getAccessibleText()) {
						case "NOTI":
							stackPane.setStyle("-fx-background-color:red");
							break;
						case "CART":
							stackPane.setStyle("-fx-background-color:red");
							break;
						case "PROFILE":
							stackPane.setStyle("-fx-background-color:red");
							break;
						default:
							break;
						}
					});
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(Hamburger);
		task.setRate(-1);
		Hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
			task.setRate(task.getRate() * -1);
			task.play();
			if (drawer.isShown()) {
				drawer.close();
				drawer.setVisible(false);
			} else {
				drawer.open();
				drawer.setVisible(true);
			}
		});

	}

}
