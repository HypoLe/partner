package com.boco.eoms.partner.inspect.mgr;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

/** 
 * Description: 巡检任务 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 12, 2012 3:00:12 PM 
 */
public interface IInspectPlanMgr {
	
	/**
	 * 产生巡检资源
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间   
	 * @param createTime     资源产生日期   
	 */
	public void generateInspectPlanRes(String cycle,String city,
			String cycleStartTime,String cycleEndTime,String createTime);
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期   
	 */
	public void generateInspectPlanItem(String city,String createTime);
	
	/**
	 * 返回巡检资源所有地市
	 * @return
	 */
	public List<Map<String,Object>> findResConfigAllCity();
	
	/**
	 * 按地市、年月查询相关计划
	 * @param city
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String,Object>> findPlanIdWithPnrDept(String city,String year,String month);
	
	
	/**
	 * 产生新的计划
	 * @param planIdList
	 * @param year
	 * @param currentMonth
	 * @param maxCycleEndTime
	 */
	public void saveNewPlan(List<Map<String,Object>> planIdList,String year,String currentMonth,String maxCycleEndTime);
	
	/**
	 * 根据上月计划批量产生新的计划
	 * @param idList
	 * @param year
	 * @param month
	 */
	public void savePlanByOldPlanBatch(final List<Map<String,Object>> idList,String year,String month);
	
	/**
	 * 根据上月计划关联的资源将本月新产生的资源关联到本月计划
	 * @param idList
	 * @param maxCycleEndTime 周期范围最大值
	 */
	public void updatePlanResAssignCurrentPlan(final List<Map<String,Object>> idList,String maxCycleEndTime);
	
	/**
	 * 将本月必须执行的巡检资源关联到合适的计划上
	 * @param year
	 * @param month
	 * @param minCycleEndTime
	 * @param maxCycleEndTime
	 */
	public void updatePlanResForceAssignMatchedPlan(String year,String month,String minCycleEndTime,String maxCycleEndTime);
	
	/**
	 * 更新巡检计划关联的资源数目
	 * @param year
	 * @param month
	 */
	public void updatePlanResNum(String year,String month);
	
	/**
	 * 查询审批角色
	 * @param roleId
	 * @param city
	 * @param country
	 * @return
	 */
	public List<Map<String,String>> findSystemSubRole(String roleId,String city,String country);
	
	/**
	 * 查询巡检计划审批对象
	 * @param planId
	 * @return JSON数据
	 */
	public String getApproveObjectJson(String planId);
	
	/**
	 * 更新巡检计划的计划完成数
	 */
	public void updateInspectPlanResDoneNum();
	
	/**
	 * 更新资源的超时未完成状态
	 * inspectState巡检状态为 2超时未关联未完成或者 3超时已关联未完成
	 * @param city
	 * @param dateTime
	 */
	public void updateInspectPlanResState(String city,String dateTime);
	
	/**
	 * 保存巡检模板历史表
	 * @param year
	 * @param month
	 */
	public void saveInspectTemplateHis(String year,String month);
	
	/**
	 *  生成巡检资源和巡检项
	 * @param ids
	 */
	public void generatePlanRes(String ids);
	/**
	 * 将上月未完成(需要完成)的计划显示出来 2013-08-23
	 * @param year
	 * @param month
	 * @param day
	 */
	public void updateBeforeMonthInspectPlan(String year,String month,String day);
	
	
}
