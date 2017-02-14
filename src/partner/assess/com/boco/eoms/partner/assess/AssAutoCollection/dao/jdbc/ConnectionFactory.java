package com.boco.eoms.partner.assess.AssAutoCollection.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static Connection conn ; 
	
	public static Connection getConnection(String jdbcConfig ,String username,String password,String dbDriver){
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(jdbcConfig,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}
