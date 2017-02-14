package com.boco.eoms.partner.res.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.mcm.zyzl.zlgl.stationdel;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public interface IPnrResConfigDaoJdbc {
	
	/**
	 * Excel导入
	 */
	public void importData(List<Object> mainList,List<Object> subList);
	
	/**
	 * Excel导出
	 */
	public List<Map<String,Object>> excelExport(String specialty,List<String> condition);
	
	/**
	 * 查询未分配周期和执行对象的资源数
	 */
	public List findUnAssignCycleAndExecuteObject(String whereSql);
	
	/**
	 * 
	 *@Description XXX
	 *@date May 14, 2013 9:29:31 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mainList
	 *@param subList
	 *@param st1
	 *@param st2
	 *@return
	 *@throws Exception
	 */
	public void importData(List<Object> mainList,List<Object> subList,PreparedStatement st1,PreparedStatement st2) throws Exception;
	/**
	 * 包站到人，资源查询相关
	 */
	public List getResPersonFlag();
	public List getPanResByUser(String userId,String deptId);
	public int updateResource(String sql);
}
