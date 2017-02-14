package com.boco.eoms.deviceManagement.common.service;

import java.util.List;
import java.util.Map;

public interface CommonSpringJdbcService {
	public int queryForInt(String sql);
	public List queryForList(String sql);
	public Map<String,Object> queryForMap(String sql);
	/**
	 * 判断子场景属于新建还是拆除：flag 0 无；1 新建；2 拆除；
	  * @author wangyue
	  * @title: selChildFlag
	  * @date Jun 17, 2015 10:06:30 AM
	  * @param sql
	  * @return String
	 */
	public String selChildFlag(String sql);
	/**
	 * 
	  * @author wangyue
	  * @title: addChildStr
	  * @date Jun 17, 2015 10:36:15 AM
	  * @param str 新建或者拆除字符串
	  * @param childSql 根据子场景id查询子场景名称的sql
	  * @param unitSql 根据单位id查询单位名称的sql
	  * @param sum 该子场景选择的总数量
	  * @return String
	 */
	public String addChildStr(String str,String childSql,String unitSql,double sum);
	
	//执行存储过程
	public boolean executeProcedure(String procedureSql);
	
}
