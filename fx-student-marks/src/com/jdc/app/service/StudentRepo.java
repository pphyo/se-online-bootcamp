package com.jdc.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jdc.app.entity.Student;

public class StudentRepo {

	private List<Student> students;
	
	private static final StudentRepo INSTANCE = new StudentRepo();
	
	private StudentRepo() {
		students = new ArrayList<>();
	}
	
	public static StudentRepo getInstance() {
		return INSTANCE;
	}

	public List<Student> search(Predicate<Student> pred) {
		return students.stream().filter(pred).collect(Collectors.toList());
	}
	
	public void upload(File file) {
		try {
			students = Files.lines(file.toPath()).skip(1).map(Student::new).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
