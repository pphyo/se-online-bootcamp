package com.solt.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.solt.dto.Category;

public class CategoryDao extends AbstractDao<Category> {

	private static final String SELECT = "select * from category where 1 = 1";
	
	@Override
	protected String insertSql() {
		return "insert into category (category_name) value (?)";
	}

	@Override
	protected String updateSql() {
		return "update category set category_name = ? where category_id = ?";
	}

	@Override
	protected String deleteSql() {
		return "delete from category where category_id = ?";
	}

	@Override
	protected String findByIdSql() {
		return SELECT.concat(" and category_id = ?");
	}

	@Override
	protected String findAllSql() {
		return SELECT;
	}

	@Override
	protected Object[] insertParams(Category t) {
		return new Object[] {t.getName()};
	}

	@Override
	protected Object[] updateParams(Category t) {
		return new Object[] {t.getName(), t.getId()};
	}

	@Override
	protected int deleteParams(Category t) {
		return t.getId();
	}

	@Override
	protected Category getObject(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		return category;
	}

}
