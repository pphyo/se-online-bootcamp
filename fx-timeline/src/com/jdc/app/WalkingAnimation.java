package com.jdc.app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WalkingAnimation extends Application {
	
	private static final Image img1 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/1.png"));
	private static final Image img2 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/2.png"));
	private static final Image img3 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/3.png"));
	private static final Image img4 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/4.png"));
	private static final Image img5 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/5.png"));
	private static final Image img6 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/6.png"));
	private static final Image img7 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/7.png"));
	private static final Image img8 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/8.png"));
	private static final Image img9 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/9.png"));
	private static final Image img10 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/10.png"));
	private static final Image img11 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/11.png"));
	private static final Image img12 = new Image(WalkingAnimation.class.getResourceAsStream("/imgs/12.png"));
	
	private Group group;
	
	@Override
	public void start(Stage stage) throws Exception {
		final ImageView iv1 = new ImageView(img1);
		final ImageView iv2 = new ImageView(img2);
		final ImageView iv3 = new ImageView(img3);
		final ImageView iv4 = new ImageView(img4);
		final ImageView iv5 = new ImageView(img5);
		final ImageView iv6 = new ImageView(img6);
		final ImageView iv7 = new ImageView(img7);
		final ImageView iv8 = new ImageView(img8);
		final ImageView iv9 = new ImageView(img9);
		final ImageView iv10 = new ImageView(img10);
		final ImageView iv11 = new ImageView(img11);
		final ImageView iv12 = new ImageView(img12);
		
		group = new Group(iv1);
		group.setTranslateX(130);
		group.setTranslateY(50);
		
		KeyFrame k1 = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv2);				
			}	
		});
		
		KeyFrame k2 = new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv3);				
			}	
		});
		
		KeyFrame k3 = new KeyFrame(Duration.millis(400), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv4);				
			}	
		});
		
		KeyFrame k4 = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv5);				
			}	
		});
		
		KeyFrame k5 = new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv6);				
			}	
		});
		
		KeyFrame k6 = new KeyFrame(Duration.millis(700), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv7);				
			}	
		});
		
		KeyFrame k7 = new KeyFrame(Duration.millis(800), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv8);				
			}	
		});
		
		KeyFrame k8 = new KeyFrame(Duration.millis(900), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv9);				
			}	
		});
		
		KeyFrame k9 = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv10);				
			}	
		});
		
		KeyFrame k10 = new KeyFrame(Duration.millis(1100), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv11);				
			}	
		});
		
		KeyFrame k11 = new KeyFrame(Duration.millis(1200), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				group.getChildren().setAll(iv12);				
			}	
		});
		
		Timeline timeline = new Timeline(k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		Scene scene = new Scene(group, 600, 500);
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
