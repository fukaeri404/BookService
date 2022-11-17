package com.hostmm.bct.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.hostmm.bct.model.LoginDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXComboBox<String> cobRole;

	@FXML
	private JFXPasswordField pfPassword;

	@FXML
	private JFXTextField tfUsername;

	@FXML
	private Label lblError;

	private LoginDAO loginDAO = new LoginDAO();

	@FXML
	void processLogin(ActionEvent event) throws IOException, SQLException {
		String username = tfUsername.getText().trim();
		String password = pfPassword.getText();
		String role = cobRole.getValue();
		if (!username.isBlank() && !password.isBlank() && !role.equals(null)) {
			boolean signinOk = loginDAO.isCredentialsValid(username, password, role);
			if (signinOk) {
				if (role.equalsIgnoreCase("admin")) {
					Stage adminStage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
					adminStage.hide();
					BorderPane root = FXMLLoader
							.load(getClass().getResource("/com/hostmm/bct/view/AdminMainView.fxml"));
					Scene scene = new Scene(root);
					adminStage.setScene(scene);
					adminStage.setTitle("Admin Main UI");
					adminStage.setMaximized(false);
					adminStage.show();
				}
			} else {
				lblError.setVisible(true);
//			String title = "Login Fail";
//			String message = "Email or Password is incorrect";
//			MyNotificationType notiType = MyNotificationType.ERROR;
//			Duration dismissTime = Duration.seconds(3);
//			myNoti.getNotification(title, message, notiType, dismissTime);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblError.setVisible(false);
		ObservableList<String> role = FXCollections.observableArrayList("Admin", "Customer");
		cobRole.setItems(role);

		btnLogin.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				btnLogin.setStyle("-fx-background-color:#EB6440");
			}
		});

		btnLogin.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				btnLogin.setStyle("-fx-background-color:#497174");

			}
		});

		BooleanBinding booleanBind = tfUsername.textProperty().isEmpty().or(pfPassword.textProperty().isEmpty())
				.or(cobRole.valueProperty().isNull());
		btnLogin.disableProperty().bind(booleanBind);
	}

}
