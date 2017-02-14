package com.boco.eoms.partner.inspect.mgr;

import java.util.Map;

import com.googlecode.genericdao.search.Search;

/** 
 * Description:  巡检人员任务查询和执行等
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Jul 18, 2012 3:36:21 PM 
 */
public interface IInspectPlanExecuteMgr {
	/**
	 * choose one between queryMap and search
	 * @param queryMap
	 * @param search
	 * @return
	 */
	public Map<String,Object> queryInspectPlanMain(Map<String, String> queryMap,Search search);
	/**
	 * choose one between queryMap and whereStr
	 * @param queryMap
	 * @param search
	 * @return
	 */
	public Map<String,Object> queryInspectPlanRes(Map<String, String> queryMap,final Integer curPage, final Integer pageSize);
	
	
	public boolean isCheckUser(String userId,String planResExecuteObject);
}
