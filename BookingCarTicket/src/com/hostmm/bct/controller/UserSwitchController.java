package com.hostmm.bct.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.hostmm.bct.model.User;
import com.hostmm.bct.model.UserDAO;
import com.hostmm.bct.utility.notification.MyNotification;
import com.hostmm.bct.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UserSwitchController implements Initializable {

	@FXML
	private TableView<User> tvUser;

	@FXML
	private JFXComboBox<String> cobRole;

	@FXML
	private TableColumn<User, String> address;

	@FXML
	private TableColumn<User, Integer> age;

	@FXML
	private TableColumn<User, Date> birthDate;

	@FXML
	private TableColumn<User, Boolean> senior;

	@FXML
	private TableColumn<User, String> firstName;

	@FXML
	private TableColumn<User, String> gender;

	@FXML
	private TableColumn<User, String> lastName;

	@FXML
	private TableColumn<User, String> nrc;

	@FXML
	private TableColumn<User, String> phone;

	@FXML
	private TableColumn<User, String> occupation;

	@FXML
	private TableColumn<User, String> userID;

	@FXML
	private TableColumn<User, String> username;

	@FXML
	private JFXTextField tfSearch;

	private UserDAO userDAO = new UserDAO();
	User user = User.getUserInstance();
	private MyNotification myNoti = new MyNotification();

	@FXML
	void processAdd(ActionEvent event) {
		this.user.setAccountHistory("");
		showRegisterView();
	}

	@FXML
	void processEdit(ActionEvent event) throws IOException {
		User selectedUser = tvUser.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			User storedUser = this.userDAO.getUser("username", String.valueOf(selectedUser.getUsername()),
					selectedUser.getRole());

			this.user.setUserID(storedUser.getUserID());
			this.user.setUsername(storedUser.getUsername());
			this.user.setPassword(storedUser.getPassword());
			this.user.setFirstName(storedUser.getFirstName());
			this.user.setLastName(storedUser.getLastName());
			this.user.setAge(storedUser.getAge());
			this.user.setBirthDate(storedUser.getBirthDate());
			this.user.setGender(storedUser.getGender());
			this.user.setNrc(storedUser.getNrc());
			this.user.setAddress(storedUser.getAddress());
			this.user.setPhone(storedUser.getPhone());
			this.user.setRole(storedUser.getRole());
			this.user.setImageName(storedUser.getImageName());
			this.user.setActive(storedUser.isActive());
			this.user.setAccountHistory(storedUser.getAccountHistory());
			if (selectedUser.getRole().equalsIgnoreCase("admin")
					|| selectedUser.getRole().equalsIgnoreCase("cashier")) {
				user.setStatus(storedUser.getStatus());
				user.setEmployDate(storedUser.getEmployDate());
				user.setResignDate(storedUser.getResignDate());
				user.setSalary(storedUser.getSalary());
				user.setSenior(storedUser.getSenior());
			} else
				user.setOccupation(storedUser.getOccupation());
			showRegisterView();
		}

	}

	void showRegisterView() {
		try {
			BorderPane root = (BorderPane) FXMLLoader
					.load(getClass().getResource("/com/hostmm/bct/view/RegisterView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Host Hospital Staff Edit");
			stage.setMaximized(false);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void processDelete(ActionEvent event) {
		User selectedUser = tvUser.getSelectionModel().getSelectedItem();
		if (selectedUser != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure to delete?");
			Optional<ButtonType> action = alert.showAndWait();
			if (action.get() == ButtonType.OK) {
				int rowEffected = this.userDAO.deleteById(selectedUser);
				if (rowEffected > 0) {
					String messageTitle = "Deleted";
					String message = "successfully deleted User";
					MyNotificationType notitype = MyNotificationType.SUCCESS;
					Duration dismissTime = Duration.seconds(3);
					myNoti.getNotification(messageTitle, message, notitype, dismissTime);
					refresh();
				}
			}

		}

	}

	@FXML
	void processRefresh(ActionEvent event) {
		refresh();
	}

	void refresh() {
		if (cobRole.getValue() != null) {
			showTable(cobRole.getValue().toLowerCase());
		}
	}

	void showTable(String role) {
		userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		age.setCellValueFactory(new PropertyValueFactory<>("age"));
		birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		nrc.setCellValueFactory(new PropertyValueFactory<>("nrc"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		senior.setCellValueFactory(new PropertyValueFactory<>("senior"));
		occupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));
		ObservableList<User> userList = this.userDAO.getUserList(role);
		tvUser.setItems(userList);
		tfSearch.textProperty().addListener(
				(observable, oldValue, newValue) -> tvUser.setItems(filterList(userList, newValue.toLowerCase())));
	}

	private ObservableList<User> filterList(List<User> list, String searchText) {
		List<User> filteredList = new ArrayList<>();

		for (User user : list) {
			if (searchFindsUser(user, searchText)) {
				filteredList.add(user);
			}
		}
		return FXCollections.observableList(filteredList);
	}

	private boolean searchFindsUser(User user, String searchText) {
		return (user.getUsername().toLowerCase().contains(searchText))
				|| (user.getUserID().toLowerCase().contains(searchText)) || (user.getPhone().contains(searchText))
				|| (user.getFirstName().toLowerCase().contains(searchText))
				|| (user.getLastName().toLowerCase().contains(searchText));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> roleList = FXCollections.observableArrayList("Admin", "Customer");
		cobRole.setItems(roleList);

		cobRole.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				showTable(newValue.toLowerCase());
				if (newValue.equals("Admin")) {
					occupation.setVisible(false);
					senior.setVisible(true);
				} else {
					occupation.setVisible(true);
					senior.setVisible(false);
				}

			}
		});

	}

}
