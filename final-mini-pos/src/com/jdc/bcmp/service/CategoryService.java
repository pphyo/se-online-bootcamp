package com.jdc.bcmp.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.util.DatabaseConnection;

public class CategoryService {
	
	private static CategoryService INSTANCE;
	private static final String SELECT = "select * from category where 1 = 1";
	
	private CategoryService() {}
	
	public static CategoryService getInstance() {
		return INSTANCE == null ? INSTANCE = new CategoryService() : INSTANCE;
	}
	
	public void save(Category c) {
		
		String sql = "insert into category (name) values (?)";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, c.getName());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Category> getAll() {
		
		List<Category> result = new ArrayList<Category>();
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				result.add(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void upload(File file) throws IOException {
		Files.lines(file.toPath()).map(s -> {
			Category c = new Category();
			c.setName(s);
			return c;
		}).forEach(this::save);
	}

}
