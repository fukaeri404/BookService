package com.hostmm.bct.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
	private SimpleStringProperty orderID;
	private SimpleStringProperty userID;
	private SimpleStringProperty orderedDate;
	private SimpleStringProperty phone;
	private SimpleStringProperty address;
	private SimpleDoubleProperty totalAmount;
	private SimpleStringProperty purchasedBooks;
	private SimpleStringProperty status;

	public Order(String orderID, String userID, LocalDate orderedDate, String phone, String address, double totalAmount,
			String purchasedBooks, String status) {
		super();
		this.orderID = new SimpleStringProperty(orderID);
		this.userID = new SimpleStringProperty(userID);
		this.orderedDate = new SimpleStringProperty(orderedDate.toString());
		this.phone = new SimpleStringProperty(phone);
		this.address = new SimpleStringProperty(address);
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
		this.purchasedBooks = new SimpleStringProperty(purchasedBooks);
		this.status = new SimpleStringProperty(status);
	}

	public String getOrderID() {
		return orderID.get();
	}

	public void setOrderID(String orderID) {
		this.orderID = new SimpleStringProperty(orderID);
	}

	public String getUserID() {
		return userID.get();
	}

	public void setUserID(String userID) {
		this.userID = new SimpleStringProperty(userID);
	}

	public LocalDate getOrderedDate() {
		LocalDate date = LocalDate.parse(this.orderedDate.get());
		return date;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = new SimpleStringProperty(orderedDate.toString());
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}

	public double getTotalAmount() {
		return totalAmount.get();
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = new SimpleDoubleProperty(totalAmount);
	}

	public String getPurchasedBooks() {
		return purchasedBooks.get();
	}

	public void setPurchasedBooks(String purchasedBooks) {
		this.purchasedBooks = new SimpleStringProperty(purchasedBooks);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone = new SimpleStringProperty(phone);
	}
}
