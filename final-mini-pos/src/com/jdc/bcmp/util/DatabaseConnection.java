package com.jdc.bcmp.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
	
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
