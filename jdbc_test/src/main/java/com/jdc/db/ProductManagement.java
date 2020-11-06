package com.jdc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdc.db.entity.Category;
import com.jdc.db.entity.Product;

public class ProductManagement {
	
	private static final String SELECT = "select c.id cat_id, c.name cat_name, "
			+ "p.id pro_id, p.name pro_name, price, p.cat_id pro_cat_id from category c"
			+ " join product p on c.id = p.cat_id where 1 = 1";
	
	public void save(Product... p) {
		
		String sql = "insert into product (name, price, cat_id) values (?, ?, ?)";
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			for(Product pro : p) {
				stmt.setString(1, pro.getName());
				stmt.setInt(2, pro.getPrice());
				stmt.setInt(3, pro.getCategory().getId());
				
				stmt.addBatch();
			}
			
			stmt.executeBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Product findById(int id) {
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT.concat(" and p.id = ?"))) {
			
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
	
	public List<Product> getAll() {
		List<Product> result = new ArrayList<>();
		
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SELECT)) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				result.add(getObject(rs));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Product getObject(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt("pro_id"));
		p.setName(rs.getString("pro_name"));
		p.setPrice(rs.getInt("price"));
		
		Category cat = new Category();
		cat.setId(rs.getInt("cat_id"));
		cat.setName(rs.getString("cat_name"));
		
		p.setCategory(cat);
		
		return p;
	}

}
