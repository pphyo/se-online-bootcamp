package com.jdc.bcmp.views;

import java.util.function.Consumer;

import com.jdc.bcmp.PosException;
import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.util.StringUtil;

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
	
	private Product p;
	private Consumer<Product> saveListener;
	
	public void initialize() {
		cbxCategory.getItems().addAll(CategoryService.getInstance().getAll());
		txtInfo.setText("");
	}
	
	public static void showView(Product p, Consumer<Product> saveListener) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(ProductEdit.class.getResource("ProductEdit.fxml"));
			Parent root = loader.load();
			
			boolean isNew = null == p;
			
			ProductEdit controller = loader.getController();
			
			if(!isNew) {
				controller.setDataToView(p);
			}
			
			controller.p = p;
			controller.saveListener = saveListener;
			
			controller.txtTitle.setText(isNew ? "Add Product" : "Edit Product");
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			if(null == p) {
				p = new Product();
			}
			
			if(null == cbxCategory.getValue())
				throw new PosException("Please select category!");
			p.setCategory(cbxCategory.getValue());
			
			if(StringUtil.isEmpty(txtName.getText()))
				throw new PosException("Please enter product name!");
			p.setName(txtName.getText());
			
			int price = StringUtil.isEmpty(txtPrice.getText()) ? 0 : Integer.parseInt(txtPrice.getText());
			if(price <= 0)
				throw new PosException("Please enter correct price!");
			p.setPrice(price);
			
			p.setDescription(txtDescription.getText());
			
			saveListener.accept(p);
			close();
			
		} catch(NumberFormatException e) {
			txtInfo.setText("Please enter digit only!");
		} catch (Exception e) {
			txtInfo.setText(e.getMessage());
		}
	}
	
	public void close() {
		txtTitle.getScene().getWindow().hide();
	}
	
	public void setDataToView(Product p) {
		cbxCategory.setValue(p.getCategory());
		txtName.setText(p.getName());
		txtPrice.setText(String.valueOf(p.getPrice()));
		txtDescription.setText(p.getDescription());
	}
	
}
