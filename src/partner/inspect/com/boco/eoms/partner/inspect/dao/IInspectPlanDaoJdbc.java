package com.boco.eoms.partner.inspect.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: Liuchang
 * @version: 1.0 Create at: Jul 12, 2012 2:44:59 PM
 */
public interface IInspectPlanDaoJdbc {

	/**
	 * 产生巡检资源
	 * 
	 * @param cycle
	 *            周期
	 * @param city
	 *            地市
	 * @param cycleStartTime
	 *            周期开始时间
	 * @param cycleEndTime
	 *            周期结束时间
	 * @param createTime
	 *            资源产生日期
	 */
	public void generateInspectPlanRes(String cycle, String city,
			String cycleStartTime, String cycleEndTime, String createTime);

	/**
	 * 产生巡检计划执行项
	 * 
	 * @param city
	 *            地市
	 * @param createTime
	 *            资源产生日期
	 */
	public void generateInspectPlanItem(String city, String createTime);

	/**
	 * 返回巡检资源所有地市
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findResConfigAllCity();

	/**
	 * 按地市、年月查询相关计划
	 * 
	 * @param city
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>> findPlanIdWithPnrDept(String city,
			String year, String month);

	/**
	 * 根据上月计划批量产生新的计划
	 * 
	 * @param idList
	 * @param year
	 * @param month
	 */
	public void savePlanByOldPlanBatch(final List<Map<String, Object>> idList,
			String year, String month);

	/**
	 * 根据上月计划关联的资源将本月新产生的资源关联到本月计划
	 * 
	 * @param idList
	 * @param maxCycleEndTime
	 *            周期范围最大值
	 */
	public void updatePlanResAssignCurrentPlan(
			final List<Map<String, Object>> idList, String maxCycleEndTime);

	/**
	 * 将本月必须执行的巡检资源关联到合适的计划上
	 * 
	 * @param year
	 * @param month
	 * @param minCycleEndTime
	 * @param maxCycleEndTime
	 */
	public void updatePlanResForceAssignMatchedPlan(String year, String month,
			String minCycleEndTime, String maxCycleEndTime);

	/**
	 * 更新未细化巡检时间的巡检计划资源的巡检开始、结束时间
	 * 
	 * @param planId
	 * @param planStartTime
	 *            巡检开始时间
	 * @param planEndTime
	 *            巡检结束时间
	 * @param currentMonth
	 *            当前月
	 */
	public void updateInspectPlanResPlanTime(String planId,
			String planStartTime, String planEndTime, String currentMonth);

	/**
	 * 更新巡检计划关联的资源数目
	 * 
	 * @param year
	 * @param month
	 */
	public void updatePlanResNum(String year, String month);

	/**
	 * 查询巡检审批角色
	 * 
	 * @param roleId
	 * @param city
	 * @param country
	 * @return
	 */
	public List<Map<String, String>> findSystemSubRole(String roleId,
			String city, String country);

	/**
	 * 更新巡检计划的计划完成数
	 */
	public void updateInspectPlanResDoneNum();

	/**
	 * 更新资源的超时未完成状态 inspectState巡检状态为 2超时未关联未完成或者 3超时已关联未完成
	 * 
	 * @param city
	 * @param dateTime
	 */
	public void updateInspectPlanResState(String city, String dateTime);

	/**
	 * 保存巡检模板历史表
	 * 
	 * @param year
	 * @param month
	 */
	public void saveInspectTemplateHis(String year, String month);

	/**
	 * 无效资源查询
	 * 
	 * @param whereStr
	 * @return
	 */
	public List getPlanErrorDistanceRes(String whereStr, int offset,
			int pagesize);

	/**
	 * 生成巡检资源和巡检项
	 * 
	 * @param ids
	 */
	public void generatePlanRes(String ids);

	/**
	 * 查询所有的资源（与计划关联）
	 * 
	 * @param whereStr
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Object> getAllWaitInspectPlanRes(String whereStr, int offset,
			int pagesize);

	/**
	 * 查询已质检的资源
	 * 
	 * @param year
	 * @param month
	 * @param whereStr
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Object> getAllAleradyInspectPlanRes(int year, int month,
			String whereStr, int offset, int pagesize);

	/**
	 * 统计巡检原任务
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFrom> countInspectPlanRes(int countType,String isPersonnel,String personnelDeptId);

	/**
	 * 统计巡检原任务
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFrom> countInspectPlanRes(int countType,
			String areaId,String isPersonnel,String personnelDeptId);
	
	/**
	 * 统计巡检原任务
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFromNew> countInspectPlanResNew(String countType,
			String areaId,String isPersonnel,String personnelDeptId,String year,String month);
	
	/**
	 * 统计巡检原任务
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFromSpecialty> countInspectPlanResSpecialty(String countType,
			String areaId,String specialty,String person,String year,String month);
	
	
	/**
	 * 统计巡检原任务
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFrom> countInspectPlanResException(int countType,String isPersonnel,String personnelDeptId);

	/**
	 * 统计巡检原任务
	 * 
	 * @param countType
	 *            统计类型
	 * @return
	 */
	public List<InspectPlanResCountFrom> countInspectPlanResException(int countType,
			String areaId,String isPersonnel,String personnelDeptId);
	
	/**
	 * 将上月未完成(需要完成)的计划显示出来 2013-08-23
	 */
	public void updateBeforeMonthInspectPlan(String year,String month,String day);
	
	/**
	 * 资源周期被修改时,将相关联的最近地一个原任务周期修改了
	 * @param planResId 资源id
	 * @param times  要修改成的时间数组
	 */
	public void changePlanResCycle(String planResId,String[] times,String cycle);
	/**
	 * 修改巡检资源--元任务经纬度
	 * @param sql
	 * @return
	 */
	public int updateResource(String sql);
}
