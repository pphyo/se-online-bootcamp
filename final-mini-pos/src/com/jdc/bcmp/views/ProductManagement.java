package com.jdc.bcmp.views;

import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.service.ProductService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductManagement {
	
	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPrice;
	@FXML
	private TableView<Product> tblList;
	private ProductService proService;
	
	public void initialize() {
		proService = ProductService.getInstance();
		List<Category> catList = CategoryService.getInstance().getAll();
		cbxCategory.getItems().addAll(catList);
	}
	
	public void add() {
//		ProductEdit.showView();
	}
	
	public void search() {
		
	}
	
	public void upload() {
		
	}
	
	public void clear() {
		cbxCategory.setValue(null);
		txtName.clear();
		txtPrice.clear();
	}

}
