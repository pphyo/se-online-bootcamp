package com.jdc.bcmp.views;

import com.jdc.bcmp.entity.Category;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CategoryBox extends HBox {
	
	public CategoryBox(Category c) {
		getStyleClass().add("cat-box");
		getStyleClass().add("ali-c");
		Label catName = new Label(c.getName());
		getChildren().add(catName);
	}

}
