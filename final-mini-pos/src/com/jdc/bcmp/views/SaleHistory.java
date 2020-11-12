package com.jdc.bcmp.views;

import java.time.LocalDate;
import java.util.List;

import com.jdc.bcmp.entity.SaleOrder;
import com.jdc.bcmp.service.SaleService;
import com.jdc.bcmp.util.StringUtil;

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
	
	private SaleService saleService;
	
	public void initialize() {
		saleService = SaleService.getInstance();
		clear();
		search();
	}
	
	public void search() {
		tblList.getItems().clear();
		int total = StringUtil.isEmpty(txtTotal.getText()) ? 0 : Integer.parseInt(txtTotal.getText());
		List<SaleOrder> list = saleService.search(dateFrom.getValue(), dateTo.getValue(), total);
		tblList.getItems().addAll(list);
	}
	
	public void clear() {
		dateFrom.setValue(LocalDate.now().minusMonths(5));
		dateTo.setValue(LocalDate.now());
		txtTotal.clear();
	}

}
