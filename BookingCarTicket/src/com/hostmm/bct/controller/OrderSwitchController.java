package com.hostmm.bct.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.hostmm.bct.model.Order;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OrderSwitchController implements Initializable {

	@FXML
	private TableColumn<Order, String> address;

	@FXML
	private JFXComboBox<String> cobState;

	@FXML
	private TableColumn<Order, String> orderID;

	@FXML
	private TableColumn<Order, Date> orderedDate;

	@FXML
	private TableColumn<Order, String> phone;

	@FXML
	private TableColumn<Order, String> purchasedBooks;

	@FXML
	private JFXTextField tfSearch;

	@FXML
	private TableColumn<Order, Double> totalAmount;

	@FXML
	private TableColumn<Order, String> userID;
	
	@FXML
	private TableView<Order> tvOrder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> status = FXCollections.observableArrayList("Request", "Confirm", "Cancel");
		cobState.setItems(status);
	}

}
