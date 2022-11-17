package com.hostmm.bct.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Notification {
	private SimpleStringProperty userID;
	private SimpleStringProperty message;
	private SimpleBooleanProperty isActive;

	public Notification(String userID, String message, boolean isActive) {
		super();
		this.userID = new SimpleStringProperty(userID);
		this.message = new SimpleStringProperty(message);
		this.isActive = new SimpleBooleanProperty(isActive);
	}

	public String getUserID() {
		return userID.get();
	}

	public void setUserID(String userID) {
		this.userID = new SimpleStringProperty(userID);
	}

	public String getMessage() {
		return message.get();
	}

	public void setMessage(String message) {
		this.message = new SimpleStringProperty(message);
	}

	public boolean isActive() {
		return isActive.get();
	}

	public void setActive(boolean isActive) {
		this.isActive = new SimpleBooleanProperty(isActive);
	}

}
