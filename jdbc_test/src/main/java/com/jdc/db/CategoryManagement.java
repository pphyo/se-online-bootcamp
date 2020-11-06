package com.jdc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdc.db.entity.Category;

public class CategoryManagement {
	
	private static final String INSERT = "insert into category (name) values (?)";
	private static final String SELECT = "select * from category where 1 = 1";
	private static final String UPATE = "update category set name = ? where id = ?";
	private static final String DELETE = "delete from category where id = ?";
	
	public void save(Category... c) {

		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			
			for(Category cat : c) {
				stmt.setString(1, cat.getName());
				stmt.addBatch();
				
				ResultSet rs = stmt.getGeneratedKeys();
				
				while(rs.next())
					cat.setId(rs.getInt("cat_id"));
			}				
			
			stmt.executeBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(int id, String name) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(UPATE)) {
			
			stmt.setString(1, name);
			stmt.setInt(2, id);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(DELETE)) {
			
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Category findById(int id) {
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT.concat(" and id = ?"))) {
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return getObject(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Category> findAll() {
		List<Category> result = new ArrayList<>();
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				result.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Category getObject(ResultSet rs) throws SQLException {
		Category c = new Category();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		return c;
	}

}
