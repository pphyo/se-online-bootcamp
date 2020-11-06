package com.solt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.solt.dto.Category;
import com.solt.dto.Product;

public class ProductDao extends AbstractDao<Product> {
	
	private static final String SELECT = "select * from product p "
			+ "join category c on p.c_id = c.category_id where 1 = 1";

	@Override
	protected String insertSql() {
		return "insert into product(c_id, product_name, price, description) values (?, ?, ?, ?)";
	}

	@Override
	protected String updateSql() {
		return "update product set c_id = ?, product_name = ?, price = ?, description = ? where product_id = ?";
	}

	@Override
	protected String deleteSql() {
		return "delete from product where product_id = ?";
	}

	@Override
	protected String findByIdSql() {
		return SELECT.concat(" and product_id = ?");
	}

	@Override
	protected String findAllSql() {
		return SELECT;
	}

	@Override
	protected Object[] insertParams(Product p) {
		return new Object[] {p.getCategory().getId(), p.getName(), p.getPrice(), p.getDescription()};
	}

	@Override
	protected Object[] updateParams(Product p) {
		return new Object[] {p.getCategory().getId(), p.getName(), p.getPrice(), p.getDescription(), p.getId()};
	}

	@Override
	protected int deleteParams(Product p) {
		return p.getId();
	}

	@Override
	protected Product getObject(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt("product_id"));
		product.setName(rs.getString("prodcut_name"));
		product.setPrice(rs.getInt("price"));
		product.setDescription(rs.getString("description"));
		
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		
		product.setCategory(category);
		
		return product;
	}

}