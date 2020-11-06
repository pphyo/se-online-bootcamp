package com.jdc.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	
	private static Properties prop;
	
	static {
		try {
			prop = new Properties();
			prop.load(new FileInputStream("application.properties"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.usr"), prop.getProperty("db.pwd"));
	}

}
