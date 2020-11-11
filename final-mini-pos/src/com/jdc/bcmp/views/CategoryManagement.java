package com.jdc.bcmp.views;

import java.io.File;
import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CategoryManagement {
	
	@FXML
	private TextField name;
	@FXML
	private TilePane container;
	
	private CategoryService catService;
	
	public void initialize() {
		catService = CategoryService.getInstance();
		search();
	}
	
	public void add() {
		if(!StringUtil.isEmpty(name.getText())) {
			Category c = new Category();
			c.setName(name.getText());
			catService.save(c);
		}
		search();
	}
	
	public void search() {
		
		container.getChildren().clear();
		
//		List<CategoryBox> list = new ArrayList<>();
//		
//		List<Category> catList = catService.getAll();
//		for(Category c : catList) {
//			CategoryBox box = new CategoryBox(c);
//			list.add(box);
//		}		
//		container.getChildren().addAll(list);
		
		List<Category> catList = catService.getAll();
		catList.stream().distinct().sorted().map(CategoryBox::new).forEach(container.getChildren()::add);
		
	}
	
	public void upload() {
		try {
			FileChooser fc = new FileChooser();
			fc.setTitle("Upload Category");
			fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
			fc.setSelectedExtensionFilter(new ExtensionFilter("txt, csv, tsv", "*.txt", "*.csv", "*.tsv"));
			File file = fc.showOpenDialog(name.getScene().getWindow());
			catService.upload(file);
			search();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
