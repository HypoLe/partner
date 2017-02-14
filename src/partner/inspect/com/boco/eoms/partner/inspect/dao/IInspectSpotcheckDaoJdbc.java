package com.boco.eoms.partner.inspect.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 4:08:45 PM 
 */
public interface IInspectSpotcheckDaoJdbc {
	/**
	 * 查询巡检抽检模板列表
	 * @param inspectTemplateId
	 * @return
	 */
	public List<Map<String,Object>> findSpotcheckTemplateList(String inspectTemplateId,String year,String month);
	
	/**
	 * 查询抽检模板已关联以及未关联的巡检项
	 * @param inspectTemplateId
	 * @param spotcheckTemplateId
	 * @return
	 */
	public List<Map<String,String>> findInspectPlanItemList(String inspectTemplateId,String spotcheckTemplateId);
	
	/**
	 * 查询出当前模板下的所有模板大项
	 * @param inspectTemplateId
	 * @return
	 */
	public List<String> findInspectTemplateAllBigItem(String inspectTemplateId,String year,String month);
	
	/**
	 * 查询所有的抽检模板项
	 * @param templateBigItemId
	 * @param inspectTemplateId
	 * @return
	 */
	public List getAllInspectTemplateItem(String templateBigItemId,String inspectTemplateId,String year,String month);
	
	/**
	 * 执行sql
	 * @param sql
	 */
	public void excuteSql(String sql);
	
	/**
	 * 巡检资源抽检列表
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public PageModel findInspectPlanResSpotcheckList(String planId,int offset,int pagesize,InspectPlanRes res);
	
	
	/**
	 * 巡检项抽检列表
	 * @param inspectTemplateId
	 * @param inspectPlanResId
	 * @return
	 */
	public List<Map<String,Object>> findInspectPlanItemSpotcheckList(String inspectTemplateId,String inspectPlanResId);
	
	/**
	 * 巡检项抽检列表
	 * @param inspectTemplateId
	 * @param inspectPlanResId
	 * @return
	 */
	public PageModel findInspectPlanResSpotcheckListMobile(String planId,int offset,int pagesize,InspectPlanRes res,HashMap<String, String> queryMap);
	
	/**
	 * 获得模板历史表
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getInspectTemplateHisList(String year,String month,int offset,int pagesize,String whereStr);
}
