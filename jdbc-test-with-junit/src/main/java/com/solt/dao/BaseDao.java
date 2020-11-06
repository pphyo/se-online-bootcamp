package com.solt.dao;

import java.util.List;

public interface BaseDao<T> {

	void save(T t);
	void update(T t);
	void delete(T t);
	T findById(int id);
	List<T> findAll();
	List<T> search(String sql, Object[] params);
	
}
