package com.jdc.bcmp.views;

import java.io.File;
import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.service.ProductService;
import com.jdc.bcmp.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ProductManagement {
	
	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPrice;
	@FXML
	private TableView<Product> tblList;
	@FXML
	private TableColumn<Product, String> colName;
	@FXML
	private Button upload;
	
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
		
		colName.setCellFactory(TextFieldTableCell.forTableColumn());
		colName.setOnEditCommit(e -> {
			Product p = e.getRowValue();
			p.setName(e.getNewValue());
			proService.save(p);
			search();
		});
		
		upload.setText("_Upload");
		
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
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Upload Product");
			fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
			fc.setSelectedExtensionFilter(new ExtensionFilter("txt, csv, tsv", "*.txt", "*.csv", "*.tsv"));
			File file = fc.showOpenDialog(txtName.getScene().getWindow());
			proService.upload(file);
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
