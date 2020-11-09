package com.jdc.bcmp;

import com.jdc.bcmp.views.RootFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MiniPosApp extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(RootFrame.class.getResource("RootFrame.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Mini POS");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
