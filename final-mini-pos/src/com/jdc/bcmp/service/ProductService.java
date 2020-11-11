package com.jdc.bcmp.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.util.DatabaseConnection;
import com.jdc.bcmp.util.StringUtil;

public class ProductService {

	private static final String SELECT = "select c.id cat_id, c.name cat_name, p.id pro_id, p.name pro_name, p.price pro_price,"
			+ " p.description pro_description from category c join product p"
			+ " on c.id = p.category_id where 1 = 1";
	private static ProductService INSTANCE;
	
	private ProductService() {}
	
	public static ProductService getInstance() {
		return null == INSTANCE ? INSTANCE = new ProductService() : INSTANCE;
	}
	
	public void save(Product p) {
		String insertSql = "insert into product (name, price, description, category_id) values (?, ?, ?, ?)";
		String updateSql = "update product set name = ?, price = ?, description = ?, category_id = ? where id = ?";	
		
		boolean isNew = p.getId() == 0;
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(isNew ? insertSql : updateSql)) {
			
			stmt.setString(1, p.getName());
			stmt.setInt(2, p.getPrice());
			stmt.setString(3, p.getDescription());
			stmt.setInt(4, p.getCategory().getId());
			if(!isNew)
				stmt.setInt(5, p.getId());
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete(Product p) {
		String sql = "delete from product where id = ?";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, p.getId());			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Product> search(Category category, String name, int price) {
		List<Product> result = new ArrayList<>();
		
		List<Object> params = new LinkedList<>();
		
		StringBuilder sb = new StringBuilder(SELECT);
		
		if(null != category) {
			sb.append(" and p.category_id = ?");
			params.add(category);
		}
		
		if(!StringUtil.isEmpty(name)) {
			sb.append(" and lower(p.name) like ?");
			params.add("%".concat(name.toLowerCase()).concat("%"));
		}
		
		if(price > 0) {
			sb.append(" and p.price >= ?");
			params.add(price);
		}
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
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
		
		Category c = new Category();
		c.setId(rs.getInt("cat_id"));
		c.setName(rs.getString("cat_name"));
		p.setCategory(c);
		
		p.setId(rs.getInt("pro_id"));
		p.setName(rs.getString("pro_name"));
		p.setPrice(rs.getInt("pro_price"));
		p.setDescription(rs.getString("pro_description"));
		
		return p;
	}
	
}
