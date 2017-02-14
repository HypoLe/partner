package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.inspect.dao.IInspectPlanDaoJdbc;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

/** 
 * Description: 巡检任务 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 12, 2012 3:00:33 PM 
 */
public class InspectPlanMgrImpl implements IInspectPlanMgr {
	private IInspectPlanDaoJdbc inspectPlanDaoJdbc;
	
	/**
	 * 产生巡检资源
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间    
	 * @param createTime     资源产生日期
	 */
	public void generateInspectPlanRes(String cycle,String city,String cycleStartTime,
				String cycleEndTime,String createTime){
		inspectPlanDaoJdbc.generateInspectPlanRes(cycle, city,cycleStartTime,cycleEndTime,createTime);
	}
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateInspectPlanItem(String city,String createTime){
		inspectPlanDaoJdbc.generateInspectPlanItem(city,createTime);
	}
	
	public List<Map<String,Object>> findResConfigAllCity(){
		return inspectPlanDaoJdbc.findResConfigAllCity();
	}

	public IInspectPlanDaoJdbc getInspectPlanDaoJdbc() {
		return inspectPlanDaoJdbc;
	}

	public void setInspectPlanDaoJdbc(IInspectPlanDaoJdbc inspectPlanDaoJdbc) {
		this.inspectPlanDaoJdbc = inspectPlanDaoJdbc;
	}
	/**
	 * 按地市、年月查询相关计划
	 * @param city
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String,Object>> findPlanIdWithPnrDept(String city,String year,String month){
		return inspectPlanDaoJdbc.findPlanIdWithPnrDept(city, year, month);
	}
	
	/**
	 * 根据上月计划批量产生新的计划
	 * @param idList
	 * @param year
	 * @param month
	 */
	public void savePlanByOldPlanBatch(final List<Map<String,Object>> idList,String year,String month){
		inspectPlanDaoJdbc.savePlanByOldPlanBatch(idList, year, month);
	}
	
	/**
	 * 根据上月计划关联的资源将本月新产生的资源关联到本月计划
	 * @param idList
	 * @param maxCycleEndTime 周期范围最大值
	 */
	public void updatePlanResAssignCurrentPlan(final List<Map<String,Object>> idList,String maxCycleEndTime){
		inspectPlanDaoJdbc.updatePlanResAssignCurrentPlan(idList, maxCycleEndTime);
	}
	
	/**
	 * 将本月必须执行的巡检资源关联到合适的计划上
	 * @param year
	 * @param month
	 * @param minCycleEndTime
	 * @param maxCycleEndTime
	 */
	public void updatePlanResForceAssignMatchedPlan(String year,String month,String minCycleEndTime,String maxCycleEndTime){
		inspectPlanDaoJdbc.updatePlanResForceAssignMatchedPlan(year, month, minCycleEndTime,maxCycleEndTime);
	}
	
	/**
	 * 更新巡检计划关联的资源数目
	 * @param year
	 * @param month
	 */
	public void updatePlanResNum(String year,String month){
		inspectPlanDaoJdbc.updatePlanResNum(year, month);
	}
	
	/**
	 * 产生新的计划(将2步操作放在一个事务中，其中任意步骤报异常则该地市产生的计划数据全部回滚)
	 * @param planIdList
	 * @param year
	 * @param currentMonth
	 * @param maxCycleEndTime
	 */
	public void saveNewPlan(List<Map<String,Object>> planIdList,String year,String currentMonth,String maxCycleEndTime){
		//步骤1：根据上月计划批量产生新的计划
		this.savePlanByOldPlanBatch(planIdList, year.toString(), currentMonth.toString());
		//步骤2：根据上月计划关联的资源将本月新产生的资源关联到本月计划
		this.updatePlanResAssignCurrentPlan(planIdList, maxCycleEndTime);
	}
	
	public List<Map<String,String>> findSystemSubRole(String roleId,String city,String country){
		return inspectPlanDaoJdbc.findSystemSubRole(roleId, city, country);
	}
	
	@SuppressWarnings("unchecked")
	public String getApproveObjectJson(String planId){
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)ApplicationContextHolder
				.getInstance().getBean("inspectPlanMainMgr");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		ITawSystemUserRefRoleManager userRefRoleMgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserRefRoleManager");
		ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String areaId = partnerDeptMgr.getPartnerDept(planMain.getPartnerDeptId()).getAreaId();
		String city = "";
		String country = areaId;
		if(6 == areaId.length()){
			city = areaId.substring(0, 4);
		}
		List<Map<String,String>> approveObjectList = Lists.newArrayList();
		List<Map<String,String>> subRoleList = findSystemSubRole(InspectConstants.PNR_MANAGER_ROLE, city, country);
		for (Map<String, String> subRoleMap : subRoleList) {
			Map<String, String> approveRoleMap = Maps.newHashMap();
			String subRoleId = subRoleMap.get("id");
			approveRoleMap.put("id", subRoleId);
			approveRoleMap.put("pId", "0");
			approveRoleMap.put("name", subRoleMap.get("subrolename"));
			approveRoleMap.put("open", "true");
			approveRoleMap.put("type", "role");
			approveObjectList.add(approveRoleMap);
			
			List<TawSystemUserRefRole> list = userRefRoleMgr.getUserbyRoleids(subRoleId);
//			if(list.size() == 0){//如果角色下没有配置用户那么角色不能被选择
//				approveRoleMap.put("nocheck", "true");
//			}
			boolean isUserExist = false; //角色下是否存在没有删除的用户
			for (TawSystemUserRefRole userRefRole : list) {
				Map<String, String> approveUserMap = Maps.newHashMap();
				approveUserMap.put("id", userRefRole.getUserid());
				approveUserMap.put("pId", subRoleId);
				String name = userMgr.getUserByuserid(userRefRole.getUserid()).getUsername();
				approveUserMap.put("name", name);
				approveUserMap.put("type", "user");
				if(!StringUtils.isEmpty(name)){
					approveObjectList.add(approveUserMap);
					isUserExist = true;
				}
			}
			if(!isUserExist){
				approveRoleMap.put("nocheck", "true");
			}
		}
		String approveObjectJson = new Gson().toJson(approveObjectList);
		return approveObjectJson;
	}
	
	/**
	 * 保存巡检模板历史表
	 * @param year
	 * @param month
	 */
	public void saveInspectTemplateHis(String year,String month){
		inspectPlanDaoJdbc.saveInspectTemplateHis(year, month);
	}

	public void updateInspectPlanResDoneNum() {
		inspectPlanDaoJdbc.updateInspectPlanResDoneNum();
	}
	
	public void updateInspectPlanResState(String city,String dateTime){
		inspectPlanDaoJdbc.updateInspectPlanResState(city,dateTime);
	}
	
	/**
	 * 生成巡检资源和巡检项
	 */
	public void generatePlanRes(String ids){
		inspectPlanDaoJdbc.generatePlanRes(ids);
	}
	/**
	 * 将上月未完成(需要完成)的计划元任务脱离计划 显示出来 2013-08-23
	 */
	public void updateBeforeMonthInspectPlan(String year,String month,String day){
		
		inspectPlanDaoJdbc.updateBeforeMonthInspectPlan( year ,month,day);
		
	}

	
}
