package com.boco.eoms.deviceManagement.common.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.ListOrderedMap;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/** 
 * Description: 支持多种数据库的通用SQL工具类
 * 				如果需要支持超过3种数据库，建议改造此类为工厂类，创建一个接口并采用多态模式创建各自数据库
 * 				的实现类，避免每个方法中都包含了每种数据库的具体实现
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Feb 25, 2013 9:33:09 AM
 */
public class CommonSqlHelper {
	public static final String ORACLE_DIALECT = "org.hibernate.dialect.OracleDialect";
	public static final String INFORMIX_DIALECT = "org.hibernate.dialect.InformixDialect";
	
	private static String CURRENT_DIALECT = null; //当前的数据库方言
	
	/**
	 * 获取当前数据库方言
	 * @return
	 */
	public static String getCurrentDialect(){
		if(CURRENT_DIALECT == null){
			CURRENT_DIALECT = ApplicationContextHolder.getInstance().getHQLDialect().trim();
		}
		return CURRENT_DIALECT;
	}
	
	/**
	 * 判断当前数据库是否是ORACLE
	 * @return
	 */
	public static boolean isOracleDialect(){
		if(ORACLE_DIALECT.equals(getCurrentDialect())){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断当前数据库是否是INFORMIX
	 * @return
	 */
	public static boolean isInformixDialect(){
		if(INFORMIX_DIALECT.equals(getCurrentDialect())){
			return true;
		}
		return false;
	}
	
	/**
	 * 格式化日期时间
	 * oracle格式为日期格式,informix继续保留成字符串格式
	 * @param dateTime
	 * @return
	 */
	public static String formatDateTime(String dateTime){
		if(isOracleDialect()){
			return "to_date('"+dateTime+"','YYYY-MM-dd hh24:mi:ss')";
		}else if(isInformixDialect()){
			return "'" + dateTime + "'";
		}else{
			return dateTime;
		}
	}
	
	/**
	 * 格式化日期时间
	 * @param dateTime
	 * @param dateTimeFlag true：都格式为日期格式；false：oracle格式为日期格式,informix继续保留成字符串格式
	 * @return
	 */
	public static String formatDateTime(String dateTime,boolean allDateTimeFlag){
		if(true){
			if(isOracleDialect()){
				return "to_date('"+dateTime+"','YYYY-MM-dd hh24:mi:ss')";
			}else if(isInformixDialect()){
				return "to_date("+dateTime+",'%Y-%m-%d %H:%M:%S')";
			}else{
				return dateTime;
			}
		}else{
			return formatDateTime(dateTime);
		}
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(String date){
		if(isOracleDialect()){
			return "to_date('"+date+"','YYYY-MM-dd')";
		}else if(isInformixDialect()){
			return "'" + date + "'";
		}else{
			return date;
		}
	}
	
	/**
	 * 获取数据库特有的获取当前日期时间的表达式
	 * @return
	 */
	public static String getCurrentDateTimeFunc(){
		if(isOracleDialect()){
			return "sysdate";
		}else if(isInformixDialect()){
			return "current";
		}else{
			return "";
		}
	}
	
	/**
	 * 格式化多个日期时间
	 * @param dateTimes
	 * @return
	 */
	public static String[] formatDateTimes(String... dateTimes){
		String[] results = new String[dateTimes.length];
		if(isOracleDialect()){
			for(int i = 0;i<dateTimes.length;i++){
				results[i] = "to_date('"+dateTimes[i]+"','YYYY-MM-dd hh24:mi:ss')";
			}
			return results;
		}else if(isInformixDialect()){
			for(int i = 0;i<dateTimes.length;i++){
				results[i] = "'" + dateTimes[i] + "'";
			}
			return results;
		}else{
			return dateTimes;
		}
	}
	
	/**
	 * 判断空
	 * @param sql
	 * @return
	 */
	public static String formatEmpty(String sql){
		return "(" + sql + " is null or " + sql + "='') ";
	}
	
	/**
	 * 判断非空
	 * @param sql
	 * @return
	 */
	public static String formatNotEmpty(String sql){
		if(isInformixDialect()){
			return "(" + sql + " is not null and " + sql + " <> '') ";
		}else if(isOracleDialect()){
			return sql + " is not null ";
		}
		return sql;
	}
	
	/**
	 * 使用Search来判断属性是否不为空
	 * @param search
	 * @param properties
	 * @return
	 */
	public static Search formatSearchNotEmpty(Search search,String...properties){
		for (String property : properties) {
			if(isOracleDialect()){
				search.addFilterNotNull(property);
			}else if(isInformixDialect()){
				search.addFilterNotNull(property).addFilterNotEqual(property, "");
			}
		}
		return search;
	}
	
	/**
	 * 使用Search来判断属性是否为空
	 * @param search
	 * @param properties
	 * @return
	 */
	public static Search formatSearchEmpty(Search search,String...properties){
		for (String property : properties) {
			if(isOracleDialect()){
				search.addFilterNull(property);
			}else if(isInformixDialect()){
				search.addFilterOr(Filter.equal(property, ""),Filter.isNull(property));
			}
		}
		return search;
	}
	
	/**
	 * 取日期的年月日时分秒
	 * (时分秒需要时请自行添加方法时间)
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static String getDateInfo(String date,String dateType){
		dateType = dateType.toLowerCase();
		String result = date;
		if(isOracleDialect()){
			if("year".equals(dateType)){
				result = "to_char(" + date + ",'yyyy')";
			}else if("month".equals(dateType)){
				result = "to_char(" + date + ",'mm')";
			}else if("day".equals(dateType)){
				result = "to_char(" + date + ",'dd')";
			}
		}else if(isInformixDialect()){
			if("year".equals(dateType)){
				result = "YEAR("+date+")";
			}else if("month".equals(dateType)){
				result = "MONTH("+date+")";
			}else if("day".equals(dateType)){
				result = "DAY("+date+")";
			}
		}
		return result;
	}
	
	/**
	 * 格式化分页语句
	 * 注意：oracle语句如果是select * from table 必须为查询的表取别名，否则会报错,必须为：select t.* from table t
	 * @param selectSql
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public static String formatPageSql(String selectSql,int offset,int pagesize){
		selectSql = selectSql.trim();
		if(selectSql.startsWith("select")){
			selectSql = selectSql.substring(7, selectSql.length());
		}
		if(isOracleDialect()){
			selectSql = "select * from (select rownum rn," + selectSql + ")where rn > " + offset + " and rn <= " + (offset + pagesize);
		}else if(isInformixDialect()){
			selectSql = "select skip "+offset+" limit "+pagesize+" " + selectSql;
		}else{
			throw new RuntimeException("未配置当前数据库的特定分页语句，请配置");
		}
		return selectSql;
	}
	/**
	 * spring jdbc查询获取结果总数
	 * @param selectSql
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static int getResultSize(String selectSql){
		int resultSize=0;
		String countSql="";
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		selectSql = selectSql.trim();
		if(selectSql.startsWith("select")){
			selectSql = selectSql.substring(7, selectSql.length());
		}
		countSql = "select count(*) as count from (select " + selectSql + ")";
		List<ListOrderedMap> list=csjs.queryForList(countSql);
		if (list!=null&&list.size()>0) {
			if(isOracleDialect()){
				resultSize=Integer.parseInt((list.get(0).get("COUNT").toString()));
			}else if(isInformixDialect()){
				resultSize=Integer.parseInt((list.get(0).get("count").toString()));
			}else{
				throw new RuntimeException("未配置当前数据库的特定分页语句，请配置");
			}
		}
		return resultSize;
	}
	
	/**
	 * 格式化创建临时表语句
	 * @param selectSql
	 * @param whereSql
	 * @param tempTableName
	 * @return
	 */
	public static String formatTempTableSql(String selectSql,String whereSql,String tempTableName){
		String sql = "";
		if(isOracleDialect()){
			sql = "create global temporary table "+tempTableName+" on commit delete rows as (" + selectSql + "where rownum=0)";
		}else if(isInformixDialect()){
			sql = selectSql + whereSql + " into temp "+tempTableName+" with no log";
		}
		return sql;
	}
	
	/**
	 * 获取表的字段名 by zhangkeqi
	 * @param conn
	 * @param tableName
	 * @param bieming
	 * @return
	 */
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
	
	/**
	 * 生成UUID
	 * @return
	 */
	public static String generateUUID() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	/**
	 * 
	 *@Description :informix和Oracle数据库查询的结果里的list里的对象转化为Map,
	 *同时key值全部转为小写,兼容informix和Oracle数据库查询结果在get(key)时因为大小写发生错误;
	 *@date Apr 27, 2013 3:02:46 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param sql
	 *@return
	 */
	public static List<ListOrderedMap> queryForListOrderedMap(String sql){
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> list1=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> resultList=new ArrayList<ListOrderedMap>();
		list1=jdbcService.queryForList(sql);
		for (int i=0;i<list1.size();i++) {
			ListOrderedMap listOrderedMap=list1.get(i);
			ListOrderedMap map=new ListOrderedMap();
			for(Object object : listOrderedMap.keySet()) {
				Object value=listOrderedMap.get(object);
				object=object.toString().toLowerCase();
				map.put(object, value);
			}
			resultList.add(map);
		}
		return resultList;
	}
	/**
	 * 
	 *@Description :informix和Oracle数据库查询的结果里的list里的对象转化为Map,同时key值全部转为小写,
	 *兼容informix和Oracle数据库查询结果在get(key)时因为大小写发生错误;
	 *@date Apr 27, 2013 3:02:46 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param sql
	 *@return
	 */
	public static List<Map> queryForMap(String sql){
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> list1=new ArrayList<ListOrderedMap>();
		List<Map> resultList=new ArrayList<Map>();
		list1=jdbcService.queryForList(sql);
		for (int i=0;i<list1.size();i++) {
			ListOrderedMap listOrderedMap=list1.get(i);
			Map map = new HashMap();
			for(Object object : listOrderedMap.keySet()) {
				Object value=listOrderedMap.get(object);
				object=object.toString().toLowerCase();
				map.put(object, value);
			}
			resultList.add(map);
		}
		return resultList;
	}
	/**
	 * 
	 *@Description :将springjdbc查出的结果的ListOrderedMap的key值全部转化为小写，这样可以兼容informix和Oracle;
	 *@date Apr 27, 2013 3:02:46 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param sql
	 *@return
	 */
	public static List<ListOrderedMap> listOrderedMapKeyToLowerCase(List<ListOrderedMap> list){
		List<ListOrderedMap> resultList=new ArrayList<ListOrderedMap>();
		for (int i=0;i<list.size();i++) {
			ListOrderedMap listOrderedMap=list.get(i);
			ListOrderedMap map = new ListOrderedMap();
			for(Object object : listOrderedMap.keySet()) {
				Object value=listOrderedMap.get(object);
				object=object.toString().toLowerCase();
				map.put(object, value);
			}
			resultList.add(map);
		}
		return resultList;
	}
}
