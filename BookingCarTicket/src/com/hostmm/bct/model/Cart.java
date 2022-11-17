package com.hostmm.bct.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cart {
	private final static ObservableList<Cart> CART_LIST = FXCollections.observableArrayList();

	public static ObservableList<Cart> getCartList() {
		return CART_LIST;
	}

	private Book book;
	private SimpleIntegerProperty quantity;
	private SimpleStringProperty QxP;
	private SimpleDoubleProperty totalAmount;

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Cart(Book book, int quantity, String QxP, double totalAmount) {
		super();
		this.book = book;
		this.quantity = new SimpleIntegerProperty(quantity);
		this.QxP = new SimpleStringProperty(QxP);
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getQxP() {
		return QxP.get();
	}

	public void setQxP(String QxP) {
		this.QxP = new SimpleStringProperty(QxP);
	}

	public double getTotalAmount() {
		return totalAmount.get();
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity = new SimpleIntegerProperty(quantity);
	}

}
