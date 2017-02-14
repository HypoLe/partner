package com.boco.eoms.partner.interfaces.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

public class JDBCManager {

	private static BasicDataSource ds;
    public static BasicDataSource getDataSource(String url,String username,String password) {
        if (ds == null) {

            ds = new BasicDataSource();
            ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setMaxActive(50);
        }
        return ds;
    }
    
//  关闭连接对象
	public static void CloseConn(Connection conn) {
		try {
			if (conn != null || !conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// 关闭命令对象
	public static void CloseStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
				st = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	// 关闭命令对象
	public static void ClosePreparedStatement(PreparedStatement com) {
		try {
			if (com != null) {
				com.close();
				com = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// 关闭记录集对象
	public static void CloseResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
