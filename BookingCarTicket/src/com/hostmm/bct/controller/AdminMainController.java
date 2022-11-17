package com.hostmm.bct.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMainController {

	@FXML
	private StackPane mainSwitchView;

	@FXML
	private Button btnUser;

	@FXML
	private Button btnOrder;

	@FXML
	private Button btnBook;

	@FXML
	void meBook(MouseEvent event) {
		btnBook.setStyle("-fx-background-color:#D8D9CF");
	}

	@FXML
	void meOrder(MouseEvent event) {
		btnOrder.setStyle("-fx-background-color:#D8D9CF");
	}

	@FXML
	void meUser(MouseEvent event) {
		btnUser.setStyle("-fx-background-color:#D8D9CF");
	}

	@FXML
	void mxBook(MouseEvent event) {
		btnBook.setStyle("-fx-background-color:transparent");
	}

	@FXML
	void mxOrder(MouseEvent event) {
		btnOrder.setStyle("-fx-background-color:transparent");
	}

	@FXML
	void mxUser(MouseEvent event) {
		btnUser.setStyle("-fx-background-color:transparent");
	}

	@FXML
	void processBookSwitch(ActionEvent event) {
		try {
			VBox switchRoot = (VBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/BookSwitch.fxml"));
			mainSwitchView.getChildren().clear();
			mainSwitchView.getChildren().add(switchRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void processOrderSwitch(ActionEvent event) {
		try {
			VBox switchRoot = (VBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/OrderSwitch.fxml"));
			mainSwitchView.getChildren().clear();
			mainSwitchView.getChildren().add(switchRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void processSignOut(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.close();
		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/LoginView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Boooks Service");
			stage.setResizable(false);
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/hostmm/bct/image/ui/bus.png")));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void processUserSwitch(ActionEvent event) {
		try {
			VBox switchRoot = (VBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/UserSwitch.fxml"));
			mainSwitchView.getChildren().clear();
			mainSwitchView.getChildren().add(switchRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
