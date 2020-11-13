package com.jdc.bcmp.views;

import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import com.jdc.bcmp.service.SaleService;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.DatePicker;

public class PosDashboard {
	
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;
	
	public void initialize() {
		dateFrom.setValue(LocalDate.now().minusDays(5));
		dateTo.setValue(LocalDate.now());
		load();
	}
	
	public void load() {
		barChart.getData().clear();
		
		
		Map<String, Map<String, Integer>> seriesData = SaleService.getInstance().findData(dateFrom.getValue(), dateTo.getValue());
		
		for(Entry<String, Map<String, Integer>> entry : seriesData.entrySet()) {
			Series<String, Integer> series = new Series<>();
			series.setName(entry.getKey());
			
			for(Entry<String, Integer> en : entry.getValue().entrySet()) {
				Data<String, Integer> data = new Data<>(en.getKey(), en.getValue());
				series.getData().add(data);
			}
			
			barChart.getData().add(series);
		}
		
		
	}

}
