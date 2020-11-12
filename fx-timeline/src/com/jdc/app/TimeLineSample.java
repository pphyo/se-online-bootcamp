package com.jdc.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimeLineSample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane pane = new AnchorPane();
		Rectangle node = new Rectangle(200, 200);
		node.setFill(Color.BLUE);
		pane.getChildren().add(node);
		
		node.setTranslateX(100);
		node.setTranslateY(50);
		
		KeyFrame frame = new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), 300));
		
		Timeline timeline = new Timeline(frame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();
		
		Scene scene = new Scene(pane, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
