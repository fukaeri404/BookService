module BookingCarTicket {
	requires javafx.controls;
	requires com.jfoenix;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	requires TrayNotification;

	opens com.hostmm.bct.main to javafx.graphics, javafx.fxml;
	opens com.hostmm.bct.controller to javafx.fxml;
	opens com.hostmm.bct.model to javafx.base;
}
