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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerMainController implements Initializable {

	@FXML
	private JFXHamburger Hamburger;

	@FXML
	private AnchorPane apEachView;

	@FXML
	private JFXComboBox<String> cobAuthor;

	@FXML
	private JFXComboBox<String> cobCategory;

	@FXML
	private JFXComboBox<String> cobLanguage;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private FlowPane flowPane;

	@FXML
	private StackPane stackPane;

	@FXML
	private TextArea taDescription;

	@FXML
	private JFXTextField tfAuthor;

	@FXML
	private JFXTextField tfBookID;

	@FXML
	private JFXTextField tfBookName;

	@FXML
	private JFXTextField tfCategory;

	@FXML
	private JFXTextField tfInStock;

	@FXML
	private JFXTextField tfLanguage;

	@FXML
	private JFXTextField tfPages;

	@FXML
	private JFXTextField tfPrice;

	@FXML
	private TextField tfQuantity;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private ImageView imgviewEachBook;

	@FXML
	private VBox vbBookView;

	private BookDAO bookDAO = new BookDAO();
	private Book book_instance = Book.getBookInstance();
	private int quantity;
	private int instock;

	@FXML
	void processSignOut(ActionEvent event) {

	}

	@FXML
	void processIncreaseQuantity(ActionEvent event) {
		if (quantity < instock) {
			quantity += 1;
			tfQuantity.setText(String.valueOf(quantity));
		}
	}

	@FXML
	void processDecreaseQuantity(ActionEvent event) {
		if (quantity > 0) {
			quantity -= 1;
			tfQuantity.setText(String.valueOf(quantity));
		}

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
		addCard(bookDAO.getBookList());

		cobAuthor.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					if (cobCategory.getValue() == null && cobLanguage.getValue() == null) // author nn, both n
						addCard(bookDAO.getOneSituationBookList("author", newValue));
					else if (cobCategory.getValue() != null && cobLanguage.getValue() != null) // author nn, both nn
						addCard(bookDAO.getThreeSituationBookList("author", newValue, "category",
								cobCategory.getValue(), "language", cobLanguage.getValue()));
					else if (cobCategory.getValue() == null && cobLanguage.getValue() != null) // author nn, cat n, lan
																								// nn
						addCard(bookDAO.getTwoSituationBookList("author", newValue, "language",
								cobLanguage.getValue()));
					else if (cobCategory.getValue() != null && cobLanguage.getValue() == null)
						addCard(bookDAO.getTwoSituationBookList("author", newValue, "category",
								cobCategory.getValue()));
				}
			}
		});

		cobCategory.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					if (cobAuthor.getValue() == null && cobLanguage.getValue() == null) // author nn, both n
						addCard(bookDAO.getOneSituationBookList("category", newValue));
					else if (cobAuthor.getValue() != null && cobLanguage.getValue() != null) // author nn, both nn
						addCard(bookDAO.getThreeSituationBookList("category", newValue, "author", cobAuthor.getValue(),
								"language", cobLanguage.getValue()));
					else if (cobAuthor.getValue() == null && cobLanguage.getValue() != null) // author nn, cat n, lan nn
						addCard(bookDAO.getTwoSituationBookList("category", newValue, "language",
								cobLanguage.getValue()));
					else if (cobAuthor.getValue() != null && cobLanguage.getValue() == null)
						addCard(bookDAO.getTwoSituationBookList("category", newValue, "author", cobAuthor.getValue()));
				}
			}
		});
		cobLanguage.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					if (cobCategory.getValue() == null && cobAuthor.getValue() == null) // author nn, both n
						addCard(bookDAO.getOneSituationBookList("language", newValue));
					else if (cobCategory.getValue() != null && cobAuthor.getValue() != null) // author nn, both nn
						addCard(bookDAO.getThreeSituationBookList("language", newValue, "category",
								cobCategory.getValue(), "author", cobAuthor.getValue()));
					else if (cobCategory.getValue() == null && cobAuthor.getValue() != null) // author nn, cat n, lan
																								// nn
						addCard(bookDAO.getTwoSituationBookList("language", newValue, "author", cobAuthor.getValue()));
					else if (cobCategory.getValue() != null && cobAuthor.getValue() == null)
						addCard(bookDAO.getTwoSituationBookList("language", newValue, "category",
								cobCategory.getValue()));
				}
			}
		});

	}

	void addCard(ObservableList<Book> bookList) {

		flowPane.getChildren().clear();
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
					vbBookView.setVisible(false);
					apEachView.setVisible(true);
					tfBookID.setText(book.getBookID());
					tfBookName.setText(book.getBookName());
					tfAuthor.setText(book.getAuthor());
					tfPages.setText(String.valueOf(book.getPages()));
					tfLanguage.setText(book.getLanguage());
					tfCategory.setText(book.getCategory());
					tfPrice.setText(String.valueOf(book.getPrice()));
					tfInStock.setText(String.valueOf(book.getInstock()));
					imgviewEachBook.setImage(new Image(
							getClass().getResourceAsStream("/com/hostmm/bct/image/book/" + book.getImageName())));
					taDescription.setText(book.getDescription());
					instock = Integer.parseInt(tfInStock.getText());

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
