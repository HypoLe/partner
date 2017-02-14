package com.boco.activiti.partner.process.webapp.action;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
/** 
 *   
 * 2015-10-26����2:45:56 
 * 
 *MusicWeb.util.DBUtil 
 *l����ݿ� �� 
 */ 
public class OracleDBUtil {  
       
	private static Connection con = null;
	
	private static ResourceBundle resource = ResourceBundle.getBundle("com/boco/activiti/partner/process/config/data_extraction");  
	private static String URL = resource.getString("ORACLE_URL");  
	private static String USER = resource.getString("ORACLE_USER");  
	private static String PASSWORD = resource.getString("ORACLE_PASSWORD");  
 
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("����Oracle��ݿ���ʧ�ܣ�");
        }
    }
 
    /**
     * ��ȡConnection
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("��ȡ��ݿ�l��ʧ�ܣ�");
            throw e;
        }
        return conn;
    }
     
    /**
     * �ر�ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
     
    /**
     * �ر�Statement
     * @param stmt
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }       
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
     
    /**
     * �ر�ResultSet��Statement
     * @param rs
     * @param stmt
     */
    public static void closeStatement(ResultSet rs, Statement stmt) {
        closeResultSet(rs);
        closeStatement(stmt);
    }
     
    /**
     * �ر�PreparedStatement
     * @param pstmt
     * @throws SQLException
     */
    public static void fastcloseStmt(PreparedStatement pstmt) throws SQLException
    {
        pstmt.close();
    }
     
    /**
     * �ر�ResultSet��PreparedStatement
     * @param rs
     * @param pstmt
     * @throws SQLException
     */
    public static void fastcloseStmt(ResultSet rs, PreparedStatement pstmt) throws SQLException
    {
        rs.close();
        pstmt.close();
    }
     
    /**
     * �ر�ResultSet��Statement��Connection
     * @param rs
     * @param stmt
     * @param con
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(con);
    }
     
    /**
     * �ر�Statement��Connection
     * @param stmt
     * @param con
     */
    public static void closeConnection(Statement stmt, Connection con) {
        closeStatement(stmt);
        closeConnection(con);
    }
     
    /**
     * �ر�Connection
     * @param con
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
               con.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
	 
   
 
    public static List<Map<String, Object>> queryMapList(Connection con, String sql) throws SQLException,
            InstantiationException, IllegalAccessException {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        Statement preStmt = null;
        ResultSet rs = null;
        try {
            preStmt = con.createStatement();
            rs = preStmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (null != rs && rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnCount; i++) {
                    String name = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(name);
                    map.put(name, value);
                }
                lists.add(map);
            }
        } finally {
            if (null != rs)
                rs.close();
            if (null != preStmt)
                preStmt.close();
        }
        return lists;
    }
 
    public static int save(Connection con, String sql) throws SQLException,
	    InstantiationException, IllegalAccessException {
		Statement preStmt = null;
		int count = 0;
		try {
		    preStmt = con.createStatement();
		    count = preStmt.executeUpdate(sql);
		} finally {
		    if (null != preStmt)
		        preStmt.close();
		}
		return count;
		}
	 
 
}
   

