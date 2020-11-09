package com.jdc.bcmp.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RootFrame {
	
	@FXML
	private Label title;
	@FXML
	private VBox menuBtnHolder;
	@FXML
	private StackPane viewHolder;
	
	public void loadDashboardView(MouseEvent event) {
		changeActive(event);
		loadView("POS Dashboard", "PosDashboard");
	}
	
	public void loadSaleView(MouseEvent event) {
		changeActive(event);
		loadView("Sale", "Sale");
	}
	
	public void loadCategoryView(MouseEvent event) {
		changeActive(event);
		loadView("Categories", "Category");
	}
	
	public void loadProductView(MouseEvent event) {
		changeActive(event);
		loadView("Products", "Product");
	}
	
	public void loadSaleHistoryView(MouseEvent event) {
		changeActive(event);
		loadView("Sale History", "SaleHistory");
	}
	
	public void changeActive(MouseEvent event) {
		Node node = (Node) event.getSource();
//		for(Node n : menuBtnHolder.getChildren()) {
//			if(n instanceof HBox) {
//				HBox box = (HBox)n;
//				if(box.getStyleClass().contains("active"))
//					box.getStyleClass().remove("active");
//			}				
//		}
		
		menuBtnHolder.getChildren().stream().filter(n -> n instanceof HBox)
								   .map(n -> (HBox)n)
								   .filter(box -> box.getStyleClass().contains("active"))
								   .findAny()
								   .ifPresent(box -> box.getStyleClass().remove("active"));
		
		node.getStyleClass().add("active");
	}
	
	public void loadView(String viewName, String viewFile) {
		
		title.setText(viewName);
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(viewFile.concat(".fxml")));
			viewHolder.getChildren().clear();
			viewHolder.getChildren().add(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	
}
