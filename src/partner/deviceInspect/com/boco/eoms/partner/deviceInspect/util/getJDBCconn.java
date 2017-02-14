package com.boco.eoms.partner.deviceInspect.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getJDBCconn {
	public static Connection getConn() {
	    String driver = "oracle.jdbc.driver.OracleDriver";
	    String url = "jdbc:oracle:thin:@192.168.10.8:1521:orcl";// 
	    String username = "partner";
	    String password = "partner";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
}
