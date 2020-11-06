package com.solt.dao;

import static com.solt.dao.SQLConnection.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class AbstractDao<T> implements BaseDao<T> {

	private final Predicate<T> pred = a -> a instanceof AutoGenerate;
	private final BiPredicate<String, Object[]> isNeedConcat = (str, arr) -> (str != null && str.isEmpty()) && (arr != null && arr.length != 0);

	@Override
	public void save(T t) {

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(insertSql(), Statement.RETURN_GENERATED_KEYS)) {

			setParams(stmt, insertParams(t));

			stmt.executeUpdate();

			if (pred.test(t)) {
				ResultSet rs = stmt.getGeneratedKeys();

				while (rs.next())
					((AutoGenerate) t).setId(rs.getInt(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(T t) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(updateSql())) {

			setParams(stmt, updateParams(t));

			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(T t) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(deleteSql())) {

			stmt.setInt(1, deleteParams(t));
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public T findById(int id) {
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(findByIdSql())) {
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				return getObject(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<>();
		
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(findAllSql())) {
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				list.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<T> search(String sql, Object[] params) {
		List<T> list = new ArrayList<>();
		boolean testResult = isNeedConcat.test(sql, params);
		
		try(Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(testResult ? findAllSql().concat(sql) : findAllSql())) {
			
			if(testResult)
				setParams(stmt, params);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
				list.add(getObject(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	private void setParams(PreparedStatement stmt, Object[] params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
	}

	protected abstract String insertSql();

	protected abstract String updateSql();

	protected abstract String deleteSql();

	protected abstract String findByIdSql();

	protected abstract String findAllSql();

	protected abstract Object[] insertParams(T t);

	protected abstract Object[] updateParams(T t);

	protected abstract int deleteParams(T t);

	protected abstract T getObject(ResultSet rs) throws SQLException;

}