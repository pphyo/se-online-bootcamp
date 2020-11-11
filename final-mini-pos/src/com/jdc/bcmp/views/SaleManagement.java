package com.jdc.bcmp.views;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.entity.SaleOrder;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SaleManagement {

	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private TextField txtProduct;
	@FXML
	private TableView<Product> tblProList;
	@FXML
	private Label headerTotal;
	@FXML
	private TableView<SaleOrder> tblCartList;
	@FXML
	private Label lblSubTotal;
	@FXML
	private Label lblTax;
	@FXML
	private Label lblTotal;

	public void search() {
		
	}
	
	public void clear() {
		
	}
	
	public void paid() {
		
	}
	
}
