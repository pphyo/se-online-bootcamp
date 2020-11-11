package com.jdc.bcmp.views;

import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.service.ProductService;
import com.jdc.bcmp.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
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
		search();
		
		MenuItem edit = new MenuItem("EDIT");
		edit.setOnAction(e -> edit());
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e -> delete());
		ContextMenu menu = new ContextMenu(edit, delete);
		tblList.setContextMenu(menu);
		
		tblList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}
	
	public void add() {
		ProductEdit.showView(null, this::save);
	}
	
	private void save(Product p) {
		proService.save(p);
		search();
	}
	
	public void search() {
		tblList.getItems().clear();
		
		int price = StringUtil.isEmpty(txtPrice.getText()) ? 0 : Integer.parseInt(txtPrice.getText());		
		List<Product> proList = proService.search(cbxCategory.getValue(), txtName.getText(), price);
		tblList.getItems().addAll(proList);
	}
	
	public void upload() {
		
	}
	
	public void edit() {
		Product p = tblList.getSelectionModel().getSelectedItem();
		ProductEdit.showView(p, this::save);
	}
	
	public void delete() {
		List<Product> list = tblList.getSelectionModel().getSelectedItems();
		list.stream().forEach(proService::delete);
		search();
	}
	
	public void clear() {
		cbxCategory.setValue(null);
		txtName.clear();
		txtPrice.clear();
	}

}
