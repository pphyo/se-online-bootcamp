package com.jdc.app;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jdc.app.entity.Student;
import com.jdc.app.service.StudentRepo;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class StudentView {
	
	@FXML
	private TextField name;
	@FXML
	private TextField total;
	@FXML
	private HBox radios;
	@FXML
	private TableView<Student> tblList;
	
	private List<RadioButton> radioList;	
	private StudentRepo repo;
	
	public void initialize() {
		repo = StudentRepo.getInstance();
		
		radioList = radios.getChildren()
						  .stream().filter(n -> n instanceof RadioButton)
						  .map(n -> (RadioButton)n)
						  .collect(Collectors.toList());
		
		
	}
	
	public void search() {
		tblList.getItems().clear();
		
		Predicate<Student> pred = a -> true;
		
		int totalMarks = total.getText().isEmpty() ? 0 : Integer.parseInt(total.getText());
		
		if(!name.getText().isBlank() && !name.getText().isEmpty())
			pred = pred.and(s -> s.getName().toLowerCase().startsWith(name.getText().toLowerCase()));
		
		if(totalMarks > 0)
			pred = pred.and(s -> s.getTotal() >= totalMarks);
		
		if(!radioList.isEmpty()) {
			for(RadioButton rb : radioList) {
				if(rb.getText().equals("Pass") && rb.isSelected())
					pred = pred.and(s -> s.getResult().toLowerCase().equals(rb.getText().toLowerCase()));
				else if(rb.getText().equals("Fail") && rb.isSelected())
					pred = pred.and(s -> s.getResult().toLowerCase().equals(rb.getText().toLowerCase()));
			}
		}
			
		
		tblList.getItems().addAll(repo.search(pred));
	}
	
	public void upload() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose File");
		fc.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
		fc.setSelectedExtensionFilter(new ExtensionFilter("Student File", ".txt", ".csv", ".tsv"));
		File file = fc.showOpenDialog(name.getScene().getWindow());
		repo.upload(file);
		search();
	}

}
