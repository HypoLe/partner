package com.boco.activiti.partner.process.webapp.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MySqlDBUtil {
private static Connection con = null;
	
	private static ResourceBundle resource = ResourceBundle.getBundle("com/boco/activiti/partner/process/config/data_extraction");//test为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可  
	private static String URL = resource.getString("MYSQL_URL");  
	private static String USER = resource.getString("MYSQL_USER");  
	private static String PASSWORD = resource.getString("MYSQL_PASSWORD");  
	  
	private static  Connection conn;  
	  
	static {  
	    try {  
	        Class.forName("com.mysql.jdbc.Driver");  
	    } catch (ClassNotFoundException e) {  
	        e.printStackTrace();  
	        throw new RuntimeException(e);  
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
    public static int getCount(Connection con, String sql) throws SQLException,
	    InstantiationException, IllegalAccessException {
	Statement preStmt = null;
	ResultSet rs = null;
	int columnCount=0;
	try {
	    preStmt = con.createStatement();
	    rs = preStmt.executeQuery(sql);
	    rs.last();
	    columnCount= rs.getRow();
	    rs.beforeFirst();
	} finally {
	    if (null != rs)
	        rs.close();
	    if (null != preStmt)
	        preStmt.close();
	}
	return columnCount;
	}
}
