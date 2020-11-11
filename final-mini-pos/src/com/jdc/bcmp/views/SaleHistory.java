package com.jdc.bcmp.views;

import com.jdc.bcmp.entity.SaleOrder;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SaleHistory {
	
	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;
	@FXML
	private TextField txtTotal;
	@FXML
	private TableView<SaleOrder> tblList;
	
	public void search() {
		
	}
	
	public void clear() {
		
	}

}
