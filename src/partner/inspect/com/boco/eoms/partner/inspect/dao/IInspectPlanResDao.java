package com.boco.eoms.partner.inspect.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0
 * Create at:   Jul 16, 2012 9:51:58 AM 
 */
public interface IInspectPlanResDao extends Dao{
	
	public Integer getPlanResCountByHql(String hql);
	
	/**
	 * 根据条件查询巡检资源列表
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findInspectPlanResList(final Integer curPage, final Integer pageSize,final String whereStr);
	
	
	
	/**
	 * 资源查询     config  2013-08-30
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findResourceList(final Integer curPage, final Integer pageSize,final String whereStr);

	
	
	
	public Map<String,Object> queryInspectPlanRes(Map<String, String> queryMap,final Integer curPage, final Integer pageSize);
	
	/**
	 * 查询巡检计划已经关联的巡检资源数量
	 * @param planId
	 * @return
	 */
	public Integer getPlanResAssignCount(String planId);
	
	
	/**
	 * 查询计划下异常数量
	 * @param planId
	 * @return
	 */
	public Integer getExceptionResCount(String planId);
	/**
	 * 查询巡检资源与资源变更情况列表
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findPlanResWithChangeList(final Integer curPage, final Integer pageSize,final String whereStr);
	
	/**
	 * 获取巡检资源与资源变更情况
	 * @param resId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPlanResWithChange(String resId);
	/**
	 *统计巡检结果
	 * @param plan_id
	 * @param executeDept
	 * @return
	 */
	public Map<String,Integer> queryTotalAndHasDoneNum(String plan_id);
	/**
	 * 查询已完成的巡检资源
	 * @param plan_id
	 * @return
	 */
	public Map<String, Integer> queryHasDoneNum(String plan_id);
	
	/**
	 * 根据条件查询巡任务列表
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getInspectPlanMainDetailList(final Integer curPage, final Integer pageSize,final String whereStr);
}
