package com.jdc.bcmp.views;

import java.util.ArrayList;
import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

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
		catList.stream().map(CategoryBox::new).forEach(container.getChildren()::add);
		
	}
	
	public void upload() {
		
	}

}
