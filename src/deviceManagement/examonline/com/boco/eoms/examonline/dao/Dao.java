package com.boco.eoms.examonline.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.common.util.StaticMethod;

public class Dao extends  HibernateDaoSupport{
	protected void populate(Object bean, ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int ncolumns = metaData.getColumnCount();

		HashMap properties = new HashMap();
		String sTemp = "";
		// Scroll to next record and pump into hashmap
		for (int i = 1; i <= ncolumns; i++) {
			// System.out.println(metaData.getColumnName(i) +":"+
			// metaData.getColumnTypeName(i));
			if (metaData.getColumnTypeName(i).toLowerCase().equals("date")
					|| metaData.getColumnTypeName(i).toLowerCase().equals(
							"datetime")
					|| metaData.getColumnTypeName(i).toLowerCase().equals(
							"datetime year to second")
					|| metaData.getColumnTypeName(i).toLowerCase().equals(
							"number")) {
				sTemp = StaticMethod.null2String(rs.getString(i)).replaceAll(
						"\\.0", "");
			} else {
				// sTemp=StaticMethod.dbNull2String(rs.getString(i));
				sTemp = StaticMethod.null2String(rs.getString(i));
			}
			properties.put(
					sql2javaName(metaData.getColumnName(i).toLowerCase()),
					sTemp);
		}
		// Set the corresponding properties of our bean
		try {
			BeanUtils.populate(bean, properties);
		} catch (InvocationTargetException ite) {
			throw new SQLException("BeanUtils.populate threw " + ite.toString());
		} catch (IllegalAccessException iae) {
			throw new SQLException("BeanUtils.populate threw " + iae.toString());
		}
	}
	protected static String sql2javaName(String name) {
		int k = name.indexOf(".");
		name = name.substring(k + 1);

		String column = "";
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '_') {
				column += ++i < name.length() ? String.valueOf(name.charAt(i))
						.toUpperCase() : "";
			} else {
				column += name.charAt(i);
			}
		}
		return column;
	}
	protected void createTempTable(String name, Connection conn)
	throws SQLException {
String sql = "create temp table " + name + "(id integer) ";
PreparedStatement pstmt = null;

try {
	pstmt = conn.prepareStatement(sql);

	pstmt.executeUpdate();
	pstmt.close();
} catch (SQLException sqle) {
	pstmt.close();
	BocoLog.error(this, 0, "建立临时表报错!", sqle);
}

// conn.commit();
}
	protected void insertTempTable(String name, Connection conn, String ids)
	throws Exception {
PreparedStatement pstmt = null;
String sql;
try {

	sql = "INSERT INTO " + name + " (id) VALUES (?)";

	ArrayList list = StaticMethod.getArrayList(ids, ",");

	for (int i = 0; i < list.size(); i++) {
		pstmt = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, StaticMethod.nullObject2int(list.get(i)));
		pstmt.executeUpdate();
		pstmt.close();
	}

} catch (Exception sqle) {
	// close(pstmt);
	BocoLog.error(this, 0, "插入临时表数据报错!", sqle);
}
}
}
