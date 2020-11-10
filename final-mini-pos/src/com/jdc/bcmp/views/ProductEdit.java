package com.jdc.bcmp.views;

import com.jdc.bcmp.entity.Category;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductEdit {

	@FXML
	private Label txtTitle;
	@FXML
	private Label txtInfo;
	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextArea txtDescription;
	
	public static void showView() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(ProductEdit.class.getResource("ProductEdit.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		
	}
	
	public void close() {
		txtTitle.getScene().getWindow().hide();
	}
	
}
