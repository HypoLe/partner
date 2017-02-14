package com.boco.eoms.partner.inspect.mgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.SpotcheckItem;
import com.boco.eoms.partner.inspect.model.SpotcheckRes;
import com.boco.eoms.partner.inspect.model.SpotcheckTemplate;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 12:48:09 PM 
 */
public interface IInspectSpotcheckMgr {
	
	
	
	/**
	 * 查询巡检抽检模板列表
	 * @param inspectTemplateId
	 * @return
	 */
	public List<Map<String,Object>> findSpotcheckTemplateList(String inspectTemplateId,String year,String month);
	
	/**
	 * 根据ID获取巡检抽检
	 * @param id
	 * @return
	 */
	public SpotcheckTemplate getSpotcheckTemplate(String id);
	
	/**
	 * 保存抽检模板
	 * @param spotcheckTemplate
	 * @param itemIdList
	 */
	public void saveSpotcheckTemplate(SpotcheckTemplate spotcheckTemplate,List<String> itemIdList);
	
	/**
	 * 保存抽检模板返回模板的id
	 * @param spotcheckTemplate
	 * @return
	 */
	public String saveSpotcheckTemplate(SpotcheckTemplate spotcheckTemplate);
	
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
	 * 删除抽检模板
	 * @param spotcheckTemplateId
	 */
	public void deleteSpotcheckTemplate(String spotcheckTemplateId);
	
	/**
	 * 删除抽检模板关联的抽检模板项
	 * @param spotcheckTemplateId
	 */
	public void deleteSpotcheckTemplateItem(String spotcheckTemplateId);
	
	/**
	 * 查询抽检模板可分配的分数
	 * @param inspectTemplateId
	 * @param spotcheckTemplateId
	 * @return
	 */
	public Float getSpotcheckTemplateUsableScore(String inspectTemplateId,String spotcheckTemplateId);
	
	/**
	 * 巡检资源抽检列表
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public PageModel findInspectPlanResSpotcheckList(String planId,int offset,int pagesize,InspectPlanRes res);
	
	/**
	 * 巡检资源抽检列表手机请求
	 * @param planId
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public PageModel findInspectPlanResSpotcheckListMobile(String planId,int offset,int pagesize,InspectPlanRes res,HashMap<String, String> queryMap);
	
	/**
	 * 巡检项抽检列表
	 * @param inspectTemplateId
	 * @param inspectPlanResId
	 * @return
	 */
	public List<Map<String,Object>> findInspectPlanItemSpotcheckList(String inspectTemplateId,String inspectPlanResId);
	
	/**
	 * 保存巡检抽检资源
	 * @param spotcheckRes
	 */
	public void saveSpotcheckRes(SpotcheckRes spotcheckRes);
	
	/**
	 * 保存巡检抽检资源项
	 * @param spotcheckItem
	 */
	public void saveSpotcheckItem(SpotcheckItem spotcheckItem);
	
	/**
	 * 获取巡检抽检资源
	 * @param spotcheckRes
	 */
	public SpotcheckRes getSpotcheckRes(String id);
	
	/**
	 * 获取巡检抽检资源项
	 * @param spotcheckItem
	 */
	public SpotcheckItem getSpotcheckItem(String id);
	
	/**
	 * 获得模板历史表
	 * @param year
	 * @param month
	 * @return
	 */
	public List getInspectTemplateHisList(String year,String month,int offset,int pagesize,String whereStr);
	
}
