package com.hostmm.bct.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public final class User {

	final static User USER_INSTANCE = new User();

	public static User getUserInstance() {
		return USER_INSTANCE;
	}

	private SimpleStringProperty userID;
	private SimpleStringProperty username;
	private SimpleStringProperty password;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleIntegerProperty age;
	private SimpleStringProperty birthDate;
	private SimpleStringProperty gender;
	private SimpleStringProperty nrc;
	private SimpleStringProperty address;
	private SimpleStringProperty phone;
	private SimpleStringProperty role;
	private SimpleStringProperty imageName;
	private SimpleBooleanProperty isActive;
	private SimpleStringProperty accountHistory;
	private SimpleBooleanProperty senior;
	private SimpleStringProperty status;
	private SimpleStringProperty employDate;
	private SimpleStringProperty resignDate;
	private SimpleDoubleProperty salary;
	private SimpleStringProperty occupation;

	public User() {
		// TODO Auto-generated constructor stub
	}

	// Admin
	public User(String userID, String username, String password, String firstName, String lastName, int age,
			LocalDate birthDate, String gender, String nrc, String address, String phone, String role, String imageName,
			boolean isActive, String accountHistory, String status, LocalDate employDate, LocalDate resignDate,
			double salary, boolean senior) {
		this.userID = new SimpleStringProperty(userID);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.age = new SimpleIntegerProperty(age);
		this.birthDate = new SimpleStringProperty(birthDate.toString());
		this.gender = new SimpleStringProperty(gender);
		this.nrc = new SimpleStringProperty(nrc);
		this.address = new SimpleStringProperty(address);
		this.phone = new SimpleStringProperty(phone);
		this.role = new SimpleStringProperty(role);
		this.imageName = new SimpleStringProperty(imageName);
		this.isActive = new SimpleBooleanProperty(isActive());
		this.accountHistory = new SimpleStringProperty(accountHistory);
		this.status = new SimpleStringProperty(status);
		this.employDate = new SimpleStringProperty(employDate.toString());
		this.resignDate = new SimpleStringProperty(resignDate.toString());
		this.salary = new SimpleDoubleProperty(salary);
		this.senior = new SimpleBooleanProperty(senior);
	}

	// customer
	public User(String userID, String username, String password, String firstName, String lastName, int age,
			LocalDate birthDate, String gender, String nrc, String address, String phone, String role, String imageName,
			boolean isActive, String accountHistory, String occupation) {
		this.userID = new SimpleStringProperty(userID);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.age = new SimpleIntegerProperty(age);
		this.birthDate = new SimpleStringProperty(birthDate.toString());
		this.gender = new SimpleStringProperty(gender);
		this.nrc = new SimpleStringProperty(nrc);
		this.address = new SimpleStringProperty(address);
		this.phone = new SimpleStringProperty(phone);
		this.role = new SimpleStringProperty(role);
		this.imageName = new SimpleStringProperty(imageName);
		this.isActive = new SimpleBooleanProperty(isActive());
		this.accountHistory = new SimpleStringProperty(accountHistory);
		this.occupation = new SimpleStringProperty(occupation);
	}

	public String getStatus() {
		return this.status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}

	public LocalDate getEmployDate() {
		LocalDate date = LocalDate.parse(this.employDate.get());
		return date;
	}

	public void setEmployDate(LocalDate employDate) {
		this.employDate = new SimpleStringProperty(employDate.toString());
	}

	public LocalDate getResignDate() {
		LocalDate date = null;
		date = LocalDate.parse(this.resignDate.get());
		return date;
	}

	public void setResignDate(LocalDate resignDate) {
		this.resignDate = new SimpleStringProperty(resignDate.toString());
	}

	public double getSalary() {
		return this.salary.get();
	}

	public void setSalary(double salary) {
		this.salary = new SimpleDoubleProperty(salary);
	}

	public String getUserID() {
		return this.userID.get();
	}

	public void setUserID(String userID) {
		this.userID = new SimpleStringProperty(userID);
	}

	public String getUsername() {
		return this.username.get();
	}

	public void setUsername(String username) {
		this.username = new SimpleStringProperty(username);
	}

	public String getPassword() {
		return this.password.get();
	}

	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}

	public String getFirstName() {
		return this.firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName = new SimpleStringProperty(firstName);
	}

	public String getLastName() {
		return this.lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName = new SimpleStringProperty(lastName);
	}

	public int getAge() {
		return this.age.get();
	}

	public void setAge(int age) {
		this.age = new SimpleIntegerProperty(age);
	}

	public String getGender() {
		return this.gender.get();
	}

	public void setGender(String gender) {
		this.gender = new SimpleStringProperty(gender);
	}

	public String getNrc() {
		return this.nrc.get();
	}

	public void setNrc(String nrc) {
		this.nrc = new SimpleStringProperty(nrc);
	}

	public String getAddress() {
		return this.address.get();
	}

	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}

	public String getPhone() {
		return this.phone.get();
	}

	public void setPhone(String phoneNumber) {
		this.phone = new SimpleStringProperty(phoneNumber);
	}

	public String getRole() {
		return this.role.get();
	}

	public void setRole(String role) {
		this.role = new SimpleStringProperty(role);
	}

	public String getImageName() {
		return this.imageName.get();
	}

	public void setImageName(String imageName) {
		this.imageName = new SimpleStringProperty(imageName);
	}

	public boolean isActive() {
		if (this.isActive == null) {
			this.isActive = new SimpleBooleanProperty(true);
		}
		return this.isActive.get();
	}

	public void setActive(boolean isActive) {
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public String getAccountHistory() {
		if (this.accountHistory == null) {
			this.accountHistory = new SimpleStringProperty("");
		}
		return this.accountHistory.get();
	}

	public void setAccountHistory(String accountHistory) {
		this.accountHistory = new SimpleStringProperty(accountHistory);
	}

	public LocalDate getBirthDate() {
		LocalDate date = LocalDate.parse(this.birthDate.get());
		return date;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = new SimpleStringProperty(birthDate.toString());
	}


	public boolean getSenior() {
		return this.senior.get();
	}

	public void setSenior(boolean senior) {
		this.senior = new SimpleBooleanProperty(senior);
	}

	public String getOccupation() {
		return this.occupation.get();
	}

	public void setOccupation(String occupation) {
		this.occupation = new SimpleStringProperty(occupation);
	}

}
