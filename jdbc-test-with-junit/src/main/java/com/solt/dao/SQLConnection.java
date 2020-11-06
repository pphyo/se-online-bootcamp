package com.solt.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLConnection {
	
	private static Properties prop;
	
	private SQLConnection() {
		throw new RuntimeException("Can't Invoke");
	}
	
	static {
		try {
			prop = new Properties();
			prop.load(new FileInputStream("app.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(prop.getProperty("database.url"), prop.getProperty("database.user"), prop.getProperty("database.pwd"));
	}
	
	public static void dropTableOnStart(String... tables) {
		String sql = "delete from %s";
		try(Connection conn = getConnection();
				Statement stmt = conn.createStatement()) {
			
			for(String tbl : tables)
				stmt.execute(String.format(sql, tbl));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
