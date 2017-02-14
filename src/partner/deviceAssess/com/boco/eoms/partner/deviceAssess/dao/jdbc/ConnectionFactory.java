package com.boco.eoms.partner.deviceAssess.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static Connection conn ; 
	
	public static Connection getConnection(){
		try {
			Class.forName("com.informix.jdbc.IfxDriver");
			conn = DriverManager.getConnection("jdbc:informix-sqli://10.110.138.20:8004/eoms35dbs:informixserver=faultserver1;NEWCODESET=gbk,8859-1,819","informix","12*nobodyknows");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
}
