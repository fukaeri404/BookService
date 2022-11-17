package com.hostmm.bct.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.hostmm.bct.model.Book;
import com.hostmm.bct.model.BookDAO;
import com.hostmm.bct.model.Cart;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.application.Platform;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
	private AnchorPane apEachView;

	@FXML
	private AnchorPane dynamicAnchorPane;

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
	private Label lblUsername;

	@FXML
	private VBox vbBookView;

	private BookDAO bookDAO = new BookDAO();
	private int quantity;
	private int instock;
	private final static ObservableList<Cart> CARTLIST = Cart.getCartList();
	private Book book = Book.getBookInstance();

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

	@FXML
	void processAddToCart(ActionEvent event) {
		CARTLIST.add(new Cart(this.book, Integer.parseInt(tfQuantity.getText()),
				tfQuantity.getText() + " * " + this.book.getPrice(),
				Double.parseDouble(tfQuantity.getText()) * this.book.getPrice()));
		addCartItem();
	}

	@FXML
	void processShowCart() {
		drawer.setDefaultDrawerSize(250);
		if (drawer.isHidden()) {
			drawer.open();
		} else {
			drawer.close();
		}
	}

	@FXML
	void processShowBooks() {
		apEachView.setVisible(false);
		vbBookView.setVisible(true);
	}

	@FXML
	void processShowNotis() {

	}

	@FXML
	void processClear() {
		addCard(bookDAO.getBookList());
		if (cobAuthor.getValue() != null) {
			cobAuthor.setValue(null);
			cobAuthor.setPromptText("");
		}
		if (cobCategory.getValue() != null) {
			cobCategory.setValue(null);
			cobCategory.setPromptText("");
		}
		if (cobLanguage.getValue() != null) {
			cobLanguage.setValue(null);
			cobLanguage.setPromptText("");
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
					FillSelectedBookInfo(book.getBookID());
					tfQuantity.setText("0");
					this.book = book;
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
			drawer.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(Hamburger);
//		task.setRate(-1);

	}

	void addCartItem() {

		try {
			VBox toolbar = FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/Toolbar.fxml"));
			for (Node node : toolbar.getChildren()) {
				if (node.getAccessibleText() != null) {
					switch (node.getAccessibleText()) {
					case "StackPane": {// start of StackPane
						for (Node node2 : ((StackPane) node).getChildren()) {
							if (node2.getAccessibleText() != null) {
								switch (node2.getAccessibleText()) {
								case "ScrollPane": {
									Node node3 = ((ScrollPane) node2).getContent();
									if (node3.getAccessibleText() != null) {
										for (Node node4 : ((StackPane) node3).getChildren()) {
											if (node4.getAccessibleText() != null) {
												switch (node4.getAccessibleText()) {
												case "FlowPane": {
													// start of flowPane
													for (Cart cart : CARTLIST) {
														AnchorPane cartItem = FXMLLoader.load(getClass()
																.getResource("/com/hostmm/bct/view/CartItem.fxml"));

														cartItem.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
															apEachView.setVisible(true);
															FillSelectedBookInfo(cart.getBook().getBookID());
															tfQuantity.setText(String.valueOf(cart.getQuantity()));

														});

														for (Node node5 : cartItem.getChildren()) {
															if (node5.getAccessibleText() != null) {
																switch (node5.getAccessibleText()) {
																case "BookName":
																	((Label) node5)
																			.setText(cart.getBook().getBookName());
																	break;
																case "QxP":
																	((Label) node5).setText(cart.getQxP());
																	break;
																case "TAmount":
																	((Label) node5).setText(
																			String.valueOf(cart.getTotalAmount()));
																	break;
																case "imageView":
																	((ImageView) node5).setImage(
																			new Image(getClass().getResourceAsStream(
																					"/com/hostmm/bct/image/book/"
																							+ cart.getBook()
																									.getImageName())));
																	break;
																default:
																	break;
																}
															}
														}
														((FlowPane) node4).getChildren().add(cartItem);
													}

												}

													break;

												default:
													break;
												}
											}
										}
									}
								}
									break;// break of flowPane
								default:
									break;
								}
							}
						}
					}
						break;// break of StackPane
					default:
						break;
					}
				}
			}
			drawer.setSidePane(toolbar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void FillSelectedBookInfo(String ID) {
		Book book = bookDAO.getBook("bookID", ID);

		tfBookID.setText(book.getBookID());
		tfBookName.setText(book.getBookName());
		tfAuthor.setText(book.getAuthor());
		tfPages.setText(String.valueOf(book.getPages()));
		tfLanguage.setText(book.getLanguage());
		tfCategory.setText(book.getCategory());
		tfPrice.setText(String.valueOf(book.getPrice()));
		tfInStock.setText(String.valueOf(book.getInstock()));
		imgviewEachBook.setImage(
				new Image(getClass().getResourceAsStream("/com/hostmm/bct/image/book/" + book.getImageName())));
		taDescription.setText(book.getDescription());
		this.instock = Integer.parseInt(tfInStock.getText());

	}

}
