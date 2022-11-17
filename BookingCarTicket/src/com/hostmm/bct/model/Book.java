package com.hostmm.bct.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

	final static Book BOOK_INSTANCE = new Book();

	public static Book getBookInstance() {
		return BOOK_INSTANCE;
	}

	private SimpleStringProperty bookID;
	private SimpleStringProperty bookName;
	private SimpleStringProperty author;
	private SimpleIntegerProperty pages;
	private SimpleStringProperty language;
	private SimpleIntegerProperty instock;
	private SimpleStringProperty category;
	private SimpleDoubleProperty price;
	private SimpleStringProperty description;
	private SimpleStringProperty imageName;
	private SimpleStringProperty bookHistory;
	private SimpleBooleanProperty isActive;

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(String bookID, String bookName, String author, int pages, String language, int instock, String category,
			double price, String description, String imageName, String bookHistory, boolean isActive) {
		super();
		this.bookID = new SimpleStringProperty(bookID);
		this.bookName = new SimpleStringProperty(bookName);
		this.author = new SimpleStringProperty(author);
		this.pages = new SimpleIntegerProperty(pages);
		this.language = new SimpleStringProperty(language);
		this.instock = new SimpleIntegerProperty(instock);
		this.category = new SimpleStringProperty(category);
		this.price = new SimpleDoubleProperty(price);
		this.description = new SimpleStringProperty(description);
		this.imageName = new SimpleStringProperty(imageName);
		this.bookHistory = new SimpleStringProperty(bookHistory);
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public String getBookID() {
		return bookID.get();
	}

	public void setBookID(String bookID) {
		this.bookID = new SimpleStringProperty(bookID);
	}

	public String getBookName() {
		return bookName.get();
	}

	public void setBookName(String bookName) {
		this.bookName = new SimpleStringProperty(bookName);
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String author) {
		this.author = new SimpleStringProperty(author);
	}

	public int getPages() {
		return pages.get();
	}

	public void setPages(int pages) {
		this.pages = new SimpleIntegerProperty(pages);
	}

	public int getInstock() {
		return instock.get();
	}

	public void setInstock(int instock) {
		this.instock = new SimpleIntegerProperty(instock);
	}

	public String getCategory() {
		return category.get();
	}

	public void setCategory(String category) {
		this.category = new SimpleStringProperty(category);
	}

	public double getPrice() {
		return price.get();
	}

	public void setPrice(double price) {
		this.price = new SimpleDoubleProperty(price);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	}

	public String getImageName() {
		return imageName.get();
	}

	public void setImageName(String imageName) {
		this.imageName = new SimpleStringProperty(imageName);
	}

	public boolean isActive() {
		return isActive.get();
	}

	public void setActive(boolean isActive) {
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public String getBookHistory() {
		if (this.bookHistory == null) {
			this.bookHistory = new SimpleStringProperty("");
		}
		return bookHistory.get();
	}

	public void setBookHistory(String bookHistory) {
		this.bookHistory = new SimpleStringProperty(bookHistory);
	}

	public String getLanguage() {
		return language.get();
	}

	public void setLanguage(String language) {
		this.language = new SimpleStringProperty(language);
	}

}
