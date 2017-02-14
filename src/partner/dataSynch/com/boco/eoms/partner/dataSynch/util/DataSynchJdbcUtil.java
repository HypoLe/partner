package com.boco.eoms.partner.dataSynch.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     Zhangkeqi
 * @version:    1.0
 * Create at:   Apr 12, 2012 11:53:11 AM
 */
public class DataSynchJdbcUtil  extends JdbcDaoSupport{
	public Connection getCon() throws Exception{
		return this.getJdbcTemplate().getDataSource().getConnection();
	}
	
	public int saveOrUpdate(String sql,Connection conn) throws Exception{
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
		}finally{
			try{
				if(stmt != null){
					stmt.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * 批量执行
	 * @param conn
	 * @param ps
	 * @throws Exception
	 */
	public void batchSaveData(Connection conn,PreparedStatement ps) throws Exception{
		
		try{
			ps.executeBatch();
		}  finally {
			try {
				if(ps != null){
					ps.clearBatch();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeConn(Connection conn){
		try {
			if(conn != null){
				if(conn.isClosed() == false){
					conn.close();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeStatement(Statement stmt ){
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeResultSet(ResultSet rs){
	    try{
	        if(rs!=null) rs.close();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	}
	
	/**
	 * 查询一列数据集合
	 * @param conn
	 * @param sql
	 * @return
	 */
	public List<Object> findOneColumnList(Connection conn,String sql){
		List<Object> resultList = new ArrayList<Object>();
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			while(set.next()){
				resultList.add(set.getObject(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
	
	public Object getOneValue(Connection conn,String selectSql) {
		Object rtValue = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSql);
			if (rs.next()) {
				rtValue = rs.getObject(1);
			}
			return rtValue;
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		return rtValue;
	}
	
	public List<Map<String,Object>> queryForList(Connection conn,String sql){
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String,Object>> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = new ArrayList<Map<String,Object>>();
			Map<Integer,String> columnNames = new HashMap<Integer,String>();
				int columnCount = rs.getMetaData().getColumnCount();
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 1;i<=columnCount;i++) {
					String columnName = rsmd.getColumnName(i);
					if(columnName==null || columnName.trim().equals("")){
						columnName = "";
					}
					columnNames.put(i, columnName.toLowerCase());
				}
				while(rs.next()) {
					Map<String,Object> rowMap = new HashMap<String,Object>();
					for(int j = 1;j<=columnCount;j++) {
						rowMap.put(columnNames.get(j), rs.getObject(j));
					}
					list.add(rowMap);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResultSet(rs);
			closeStatement(stmt);
		}
		return list;
	}
	
	public static List<Map<String,Object>> resultSet2ListMap(ResultSet rs) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<Integer,String> columnNames = new HashMap<Integer,String>();
		try {
			int columnCount = rs.getMetaData().getColumnCount();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1;i<=columnCount;i++) {
				String columnName = rsmd.getColumnName(i);
				if(columnName==null || columnName.trim().equals("")){
					columnName = "";
				}
				columnNames.put(i, columnName.toLowerCase());
			}
			while(rs.next()) {
				Map<String,Object> rowMap = new HashMap<String,Object>();
				for(int j = 1;j<=columnCount;j++) {
					rowMap.put(columnNames.get(j), rs.getObject(j));
				}
				list.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public static String getTableColumnNames(Connection conn,String tableName,String bieming) {
		ResultSet rs = null;
		Statement stmt = null;
		StringBuilder sb = new StringBuilder();
		try {
			stmt = conn.createStatement();
			if(CommonSqlHelper.isOracleDialect()) {
				rs = stmt.executeQuery("select * from "+tableName + " where rownum=0");
			} else {
				rs = stmt.executeQuery("select first 1 * from "+tableName + " where 1<>1");
			}
			
			@SuppressWarnings("unused")
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			int columnCount = rs.getMetaData().getColumnCount();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1;i<=columnCount;i++) {
				if(!"".equals(bieming)) {
					sb.append(bieming+".");
				}
				sb.append(rsmd.getColumnName(i));
				if(i != columnCount) {
					sb.append(",");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
