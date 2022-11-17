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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

import com.hostmm.bct.model.User;
import com.hostmm.bct.model.UserDAO;
import com.hostmm.bct.model.CommonDAO;
import com.hostmm.bct.model.LoginDAO;
import com.hostmm.bct.utility.crypto.PasswordEncoder;
import com.hostmm.bct.utility.notification.MyNotification;
import com.hostmm.bct.utility.notification.MyNotificationType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RegisterController implements Initializable {

	@FXML
	private JFXCheckBox cbIsSenior;

	@FXML
	private JFXComboBox<String> cobGender;

	@FXML
	private JFXComboBox<String> cobOccupation;

	@FXML
	private JFXTextArea taAddress;

	@FXML
	private JFXComboBox<String> cobRole;

	@FXML
	private DatePicker dpBirthDate;

	@FXML
	private DatePicker dpEmployDate;

	@FXML
	private DatePicker dpResignDate;

	@FXML
	private ImageView imgviewError;

	@FXML
	private ImageView imgviewProfile;

	@FXML
	private Label lblError;

	@FXML
	private Label lbluserID;

	@FXML
	private JFXPasswordField pfPassword;

	@FXML
	private JFXPasswordField pfRePassword;

	@FXML
	private JFXRadioButton rbResign;

	@FXML
	private JFXRadioButton rbWorking;

	@FXML
	private ToggleGroup toggleStatus;

	@FXML
	private JFXTextField tfAge;

	@FXML
	private JFXTextField tfFirstName;

	@FXML
	private JFXTextField tfLastName;

	@FXML
	private JFXTextField tfNrc;

	@FXML
	private JFXTextField tfPhone;

	@FXML
	private JFXTextField tfSalary;

	@FXML
	private JFXTextField tfUsername;

	private CommonDAO commonDAO = new CommonDAO();
	private UserDAO userDAO = new UserDAO();
	private String accountHistory;
	private String currentImageName;
	private File imageFile;
	private MyNotification myNoti = new MyNotification();
	private String loggedUserID = LoginDAO.getLoggedUserID();
	User user = User.getUserInstance();

	@FXML
	void processCancel(ActionEvent event) {
		Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	void processConfirm(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		User user = null;
		String messageTitle = "";
		String message = "";
		int rowEffected = 0;
		LocalDate employDate = null;
		LocalDate resignDate = null;
		double salary = 0.0;
		String status = "";
		String occupation = "";
		boolean isSenior = false;

		String userID = lbluserID.getText().trim();
		String username = tfUsername.getText().trim();
		String rawPassword = pfPassword.getText();
		String password = "";
		if (rawPassword == null || rawPassword.isBlank())
			password = this.user.getPassword();
		else
			password = PasswordEncoder.encodePassword(rawPassword);

		String firstName = tfFirstName.getText().trim();
		String lastName = tfLastName.getText().trim();
		String role = cobRole.getValue();
		LocalDate birthDate = dpBirthDate.getValue();
		int age = Integer.parseInt(tfAge.getText());
		String gender = cobGender.getValue();
		String nrc = tfNrc.getText();
		String phone = tfPhone.getText().trim();
		String address = taAddress.getText().trim();

		String imageName = UUID.randomUUID().toString() + ".jpg";
		

		if (role.equals("Customer")) {
			occupation = cobOccupation.getValue();
		}
		if (role.equals("Admin")) {
			employDate = dpEmployDate.getValue();
			resignDate = dpResignDate.getValue();
			salary = Double.parseDouble(tfSalary.getText().trim());
			status = toggleStatus.getSelectedToggle().getUserData().toString();
			isSenior = cbIsSenior.isSelected();
		}

		if (this.accountHistory.isBlank())
			this.accountHistory = "createdBy-" + loggedUserID + "@" + LocalDate.now();
		else
			this.accountHistory = this.accountHistory + ", modifiedBy-" + loggedUserID + "@" + LocalDate.now();

		if (this.imageFile == null)
			imageName = this.currentImageName;

		File outputFile = new File("src/com/hostmm/bct/image/profile/" + imageName);
		
		if (status.equalsIgnoreCase("Working"))
			resignDate = employDate;

		if (role.equalsIgnoreCase("admin"))
			user = new User(userID, username, password, firstName, lastName, age, birthDate, gender, nrc, address,
					phone, role, imageName, true, this.accountHistory, status, employDate, resignDate, salary,
					isSenior);
		else
			user = new User(userID, username, password, firstName, lastName, age, birthDate, gender, nrc, address,
					phone, role, imageName, true, this.accountHistory, occupation);

		if (!this.accountHistory.contains("modifiedBy")) {
			rowEffected = userDAO.createUser(user, role);
			messageTitle = "Created";

		} else {
			messageTitle = "Updated";
			System.out.println("DID");
			rowEffected = userDAO.updateUser(user, role);
			System.out.println(rowEffected);
		}

		if (rowEffected > 0) {
			if (this.imageFile != null) {
				imageUpload(this.imageFile, outputFile);
				if (this.accountHistory.contains("modifiedBy")) {
					File deletedImage = new File("src/com/hostmm/bct/image/profile/" + this.currentImageName);
					if (deletedImage != null)
						Files.delete(deletedImage.toPath());
				}

				if (messageTitle.equalsIgnoreCase("created")) {
					message = "successfully created new User";
					MyNotificationType notitype = MyNotificationType.SUCCESS;
					Duration dismissTime = Duration.seconds(3);
					myNoti.getNotification(messageTitle, message, notitype, dismissTime);
				} else if (messageTitle.equalsIgnoreCase("updated")) {
					message = "successfully updated new User";
					MyNotificationType notitype = MyNotificationType.SUCCESS;
					Duration dismissTime = Duration.seconds(3);
					myNoti.getNotification(messageTitle, message, notitype, dismissTime);
				}

			}

			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	void processReset(ActionEvent event) {
		initialUI();
	}

	@FXML
	void processSignOut(ActionEvent event) {

	}

	@FXML
	void processProfileImage(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg"));
		File imageFile = fileChooser.showOpenDialog(null);
		if (imageFile != null) {
			Image image = new Image(imageFile.getAbsolutePath());
			imgviewProfile.setImage(image);
			this.imageFile = imageFile;
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

	void initialUI() {
		lbluserID.setText("userID");
		imgviewProfile.setImage(new Image(getClass().getResourceAsStream("/com/hostmm/bct/image/ui/profile.png")));
		tfUsername.setText(null);
		tfUsername.setPromptText(" Username");
		pfPassword.setText(null);
		pfPassword.setPromptText(" Password");
		pfRePassword.setText(null);
		pfRePassword.setPromptText(" Retype Password");
		tfFirstName.setText(null);
		tfFirstName.setPromptText(" First Name");
		tfLastName.setText(null);
		tfLastName.setPromptText(" Last Name");
		tfAge.setText(null);
		tfAge.setPromptText(" Age");
		tfNrc.setText(null);
		tfNrc.setPromptText(" NRC Number");
		tfPhone.setText(null);
		tfPhone.setPromptText(" Phone Number");
		taAddress.setText(null);
		tfSalary.setText(null);
		tfSalary.setVisible(false);
		tfSalary.setPromptText(" Salary");

		cobRole.setValue(null);
		cobRole.setPromptText(" Choose Role");
		cobGender.setValue(null);
		cobGender.setPromptText(" Choose Gender");
		cobOccupation.setValue(null);
		cobOccupation.setVisible(false);
		cobOccupation.setPromptText(" Choose Your Occupation");

		dpBirthDate.setValue(null);
		dpBirthDate.setPromptText(" Birth Date");
		dpEmployDate.setValue(null);
		dpEmployDate.setVisible(false);
		dpEmployDate.setPromptText(" Employ Date");
		dpResignDate.setValue(null);
		dpResignDate.setVisible(false);
		dpResignDate.setPromptText(" Resign Date");

		cbIsSenior.setVisible(false);
		cbIsSenior.setSelected(false);

		rbResign.setSelected(false);
		rbWorking.setSelected(false);
		rbResign.setVisible(false);
		rbWorking.setVisible(false);

	}

	void existingUI(User user) {
		lbluserID.setText(user.getUserID());
		tfUsername.setText(user.getUsername());
		tfFirstName.setText(user.getFirstName());
		tfLastName.setText(user.getLastName());
		cobRole.setValue(user.getRole());
		dpBirthDate.setValue(user.getBirthDate());
		tfAge.setText(String.valueOf(user.getAge()));
		cobGender.setValue(user.getGender());
		tfNrc.setText(user.getNrc());
		tfPhone.setText(user.getPhone());
		taAddress.setText(user.getAddress());
		lbluserID.setText(user.getUserID());
		cobRole.setDisable(true);
		this.currentImageName = user.getImageName();
		File imageFile = new File("src/com/hostmm/bct/image/profile/" + this.currentImageName);
		if (imageFile != null) {
			Image imageBook = new Image(imageFile.getAbsolutePath());
			System.out.println(imageFile.getAbsolutePath());
			imgviewProfile.setImage(imageBook);
		}
	}

	void staffExistingUI(User user) {
		cbIsSenior.setVisible(true);
		cbIsSenior.setSelected(user.getSenior());
		dpEmployDate.setVisible(true);
		dpEmployDate.setValue(user.getEmployDate());
		rbWorking.setVisible(true);
		rbResign.setVisible(true);
		tfSalary.setVisible(true);
		tfSalary.setText(String.valueOf(user.getSalary()));
		if (user.getStatus().equalsIgnoreCase("Working")) {
			rbWorking.setSelected(true);
		} else {
			rbResign.setSelected(true);
			dpResignDate.setValue(user.getResignDate());
			dpResignDate.setVisible(true);
		}
	}

	void customerExistingUI(User user) {
		cobOccupation.setVisible(true);
		cobOccupation.setValue(user.getOccupation());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rbWorking.setUserData("Working");
		rbResign.setUserData("Resign");
		initialUI();

		this.accountHistory = user.getAccountHistory();
		if (!this.accountHistory.isBlank()) {
			existingUI(user);
			if (user.getRole().equalsIgnoreCase("admin"))
				staffExistingUI(user);
			else
				customerExistingUI(user);

		}

		ObservableList<String> role = FXCollections.observableArrayList("Admin", "Customer");
		ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
		ObservableList<String> occupation = FXCollections.observableArrayList("Student", "Employee");

		cobRole.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				lbluserID.setText(generateUserID(commonDAO.getRowCount(newValue), newValue));
				if (newValue.equals("Admin")) {
					dpEmployDate.setVisible(true);
					rbWorking.setVisible(true);
					rbResign.setVisible(true);
					tfSalary.setVisible(true);
					cbIsSenior.setVisible(true);
					cobOccupation.setVisible(false);
				} else {
					dpEmployDate.setVisible(false);
					rbWorking.setVisible(false);
					rbResign.setVisible(false);
					tfSalary.setVisible(false);
					cbIsSenior.setVisible(false);
					cobOccupation.setVisible(true);
				}
			}
		});

		toggleStatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if (toggleStatus.getSelectedToggle() != null) {
					if (newValue.getUserData().equals(rbWorking.getUserData())) {
						dpResignDate.setVisible(false);
					} else {
						dpResignDate.setVisible(true);
					}

				}
			}
		});

		cobRole.setItems(role);
		cobGender.setItems(gender);
		cobOccupation.setItems(occupation);

	}

	private String generateUserID(int rowCount, String role) {
		rowCount += 1;
		String userID = "";

		if (rowCount < 10)
			userID = role + "-000" + rowCount;
		else if (rowCount > 9 && rowCount < 100)
			userID = role + "-00" + rowCount;
		else
			userID = role + "-0" + rowCount;

		return userID;
	}

}
