package com.hostmm.bct.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			VBox root = (VBox) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/LoginView.fxml"));
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/hostmm/bct/view/CustomerMainView.fxml"));
			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Boooks Service");
			primaryStage.setResizable(false);
			primaryStage.getIcons()
			.add(new Image(getClass().getResourceAsStream("/com/hostmm/bct/image/ui/bus.png")));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
