package com.hostmm.bct.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

import com.hostmm.bct.model.Book;
import com.hostmm.bct.model.BookDAO;
import com.hostmm.bct.model.CommonDAO;
import com.hostmm.bct.model.LoginDAO;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class BookRegisterController implements Initializable {

	@FXML
	private ComboBox<String> cobCategory;

	@FXML
	private ImageView imgviewBook;

	@FXML
	private TextArea taDescription;

	@FXML
	private TextField tfAuthor;

	@FXML
	private JFXTextField tfBookID;

	@FXML
	private TextField tfInstock;

	@FXML
	private TextField tfName;

	@FXML
	private TextField tfPages;

	@FXML
	private TextField tfPrice;

	@FXML
	private ComboBox<String> cobLanguage;

	private File imageFile;
	private String currentImageName;
	private String bookHistory;
	private Book book = Book.getBookInstance();
	private String loggedUserID = LoginDAO.getLoggedUserID();
	private BookDAO bookDAO = new BookDAO();
	private CommonDAO commonDAO = new CommonDAO();

	@FXML
	void processBookImage(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
		File imageFile = fileChooser.showOpenDialog(null);
		if (imageFile != null) {
			Image image = new Image(imageFile.getAbsolutePath());
			imgviewBook.setImage(image);
			this.imageFile = imageFile;
		}
	}

	@FXML
	void processCancel(ActionEvent event) {

	}

	@FXML
	void processCreate(ActionEvent event) throws IOException {
		int rowEffected = 0;

		String bookID = tfBookID.getText().trim();
		String bookName = tfName.getText().trim();
		String author = tfAuthor.getText().trim();
		int pages = Integer.parseInt(tfPages.getText().trim());
		String language = cobLanguage.getValue();
		int instock = Integer.parseInt(tfInstock.getText().trim());
		String category = cobCategory.getValue();
		double price = Double.parseDouble(tfPrice.getText().trim());
		String description = taDescription.getText().trim();
		String imageName = UUID.randomUUID().toString() + ".jpg";

		if (this.bookHistory.isBlank())
			this.bookHistory = "createdBy-" + loggedUserID + "@" + LocalDate.now();
		else
			this.bookHistory = this.bookHistory + ", modifiedBy-" + loggedUserID + "@" + LocalDate.now();

		if (this.imageFile == null)
			imageName = this.currentImageName;

		Book book = new Book(bookID, bookName, author, pages, language, instock, category, price, description,
				imageName, bookID, true);

		File outputFile = new File("src/com/hostmm/bct/image/book/" + imageName);

		if (!this.bookHistory.contains("modifiedBy"))
			rowEffected = bookDAO.createBook(book);
		else
			rowEffected = bookDAO.updateBook(book);

		if (rowEffected > 0) {
			if (this.imageFile != null) {
				imageUpload(this.imageFile, outputFile);
				if (this.bookHistory.contains("modifiedBy")) {
					File deletedImage = new File("src/com/hostmm/bct/image/book/" + this.currentImageName);
					if (deletedImage != null)
						Files.delete(deletedImage.toPath());
				}
			}
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}

	private void imageUpload(File inputFile, File outputFile) throws IOException {

		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(this.imageFile));
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
			byte[] byteBuffer = new byte[4000];
			int numOfByte = 0;
			while ((numOfByte = bufferedInputStream.read(byteBuffer)) != -1) {
				bufferedOutputStream.write(byteBuffer, 0, numOfByte);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> category = FXCollections.observableArrayList("Action", "Sci-Fi", "Sport", "Fantasy",
				"Horror");
		ObservableList<String> language = FXCollections.observableArrayList("English", "Myanmar");

		cobCategory.setItems(category);
		cobLanguage.setItems(language);

		this.bookHistory = book.getBookHistory();
		tfBookID.setText(generateUserID(commonDAO.getRowCount("book")));
		if (!book.getBookHistory().isBlank()) {
			tfBookID.setText(book.getBookID());
			tfName.setText(book.getBookName());
			tfAuthor.setText(book.getAuthor());
			tfPages.setText(String.valueOf(book.getPages()));
			cobLanguage.setValue(book.getLanguage());
			tfInstock.setText(String.valueOf(book.getInstock()));
			cobCategory.setValue(book.getCategory());
			tfPrice.setText(String.valueOf(book.getPrice()));
			taDescription.setText(book.getDescription());
			this.currentImageName = book.getImageName();
			File imageFile = new File("src/com/hostmm/bct/image/book/" + this.currentImageName);
			if (imageFile != null) {
				Image imageBook = new Image(imageFile.getAbsolutePath());
				imgviewBook.setImage(imageBook);
			}
		}
	}

	private String generateUserID(int rowCount) {
		rowCount += 1;
		String bookID = "";

		if (rowCount < 10)
			bookID = "Book-0000" + rowCount;
		else if (rowCount > 9 && rowCount < 100)
			bookID = "Book-000" + rowCount;
		else
			bookID = "Book-00" + rowCount;

		return bookID;
	}

}
