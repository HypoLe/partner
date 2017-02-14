package com.boco.eoms.partner.assess.AssAutoCollection.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.boco.eoms.base.dao.jdbc.BaseDaoJdbc;
import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionQueryJdbcDao;

public class AssCollectionQueryJDBC  extends BaseDaoJdbc implements AssCollectionQueryJdbcDao{

	public float getCollectionResult(String jdbcConfig ,String username,String password,String dbDriver,String sql){
		
		float i = 0.0f;				
		
		Connection conn = ConnectionFactory.getConnection(jdbcConfig ,username,password,dbDriver);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			if(rs.next()){
				i = Float.parseFloat(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
}
