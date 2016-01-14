package com.superDaxue.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection implements IConnection{
	private static final String DriverCLASS="com.mysql.jdbc.Driver";
	private static final String CONFIGNAME = "config.properties";
	
	private Connection connection;
	static{
		try {
			Class.forName(DriverCLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnect() {
		Properties prop = new Properties();
		  //加载配置文件
		try {
			prop.load(this.getClass().getResourceAsStream(CONFIGNAME));//获取数据库驱动
			connection = DriverManager.getConnection(prop.getProperty("SQLURL"), prop.getProperty("USERBANE"), prop.getProperty("PASSWORD"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
