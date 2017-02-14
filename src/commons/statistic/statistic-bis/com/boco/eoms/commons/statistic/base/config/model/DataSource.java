package com.boco.eoms.commons.statistic.base.config.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.support.JdbcUtils;

/**
 * 数据库连接
 * @author 李振友
 *
 */
public class DataSource implements Serializable{
	
	//驱动
	private String driverClass = null;
	
	//url
	private String driverUrl = null;
	
	//用户名
	private String user = null;
	
	//密码
	private String password = null;
	
	//数据库编码
	private String chatSet = "ISO-8859-1";

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Connection getConnection()
	{
		Connection conn = null;
		
		try {
			Class.forName(driverClass).newInstance();
			conn = DriverManager.getConnection(driverUrl,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public ResultSet executeQuery(String sql)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		 
		conn = getConnection();
		if(conn != null)
		{
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
		    {
				closeDB(conn,stmt);
		    }
		}
		
		return rs;
	}
	
	public boolean vilidataDataSource()
	{
		boolean v = false;
		if(driverClass != null && !"".equalsIgnoreCase(driverClass)
				&& driverUrl != null && !"".equalsIgnoreCase(driverUrl))
		{
			v = true;
		}
		
		return v;
	}
	
	private void closeDB(Connection conn,Statement stmt)
	{
    	if(stmt != null)
    	{
    		 try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
    	}
    	if(conn != null)
    	{
    		try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
    	}
	}
	
	public static void main(String[] args)
	{
		DataSource ds = new DataSource();
//		ds.setDriverClass("oracle.jdbc.driver.OracleDriver");
//		ds.setDriverUrl("jdbc:oracle:thin:@10.0.2.108:1521:eoms");
//		ds.setUser("eomsdev");
//		ds.setPassword("eomsdev");
		
		ds.setDriverClass("com.informix.jdbc.IfxDriver");
		ds.setDriverUrl("jdbc:informix-sqli://10.0.2.113:8004/test:INFORMIXSERVER=eomsserverpro");
		ds.setUser("informix");
		ds.setPassword("informix");
		Connection conn = ds.getConnection();
		System.out.println(conn);
	}

	public String getChatSet() {
		return chatSet;
	}

	public void setChatSet(String chatSet) {
		this.chatSet = chatSet;
	}
}
