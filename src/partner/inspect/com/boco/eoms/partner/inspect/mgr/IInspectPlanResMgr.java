package com.boco.eoms.partner.inspect.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanItemCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: Liuchang
 * @version: 1.0 Create at: Jul 16, 2012 10:47:46 AM
 */
public interface IInspectPlanResMgr {

	public InspectPlanRes get(Long id);

	public void save(InspectPlanRes inspectPlanRes);

	/**
	 * 根据条件查询巡检资源列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findInspectPlanResList(final Integer curPage,
			final Integer pageSize, final String whereStr);
	
	
	/**
	 *  资源查询  config表 2013-08-30
	 */
	@SuppressWarnings("unchecked")
	public Map findResourceList(final Integer curPage, 
			final Integer pageSize, final String whereStr);
	

	/**
	 * 根据HQL语句更新巡检资源关联的巡检计划
	 * 
	 * @param planId
	 * @param where
	 */
	public void updateInspectPlanResByHql(String planId, String where);

	/**
	 * 通过HQL更新巡检资源
	 * 
	 * @param hql
	 */
	public void updateInspectPlanResByHql(String hql);

	/**
	 * 查询巡检计划已经关联的巡检资源数量
	 * 
	 * @param planId
	 * @return
	 */
	public Integer getPlanResAssignCount(String planId);

	/**
	 * 查询计划下异常数量
	 * 
	 * @param planId
	 * @return
	 */
	public Integer getExceptionResCount(String planId);

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
	 * 查询巡检资源与资源变更情况列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findPlanResWithChangeList(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 查询巡检资源与资源变更情况列表
	 */
	public PageModel findPlanResWithChangeList(final String hql,
			final Object[] params, final int offset, final int pagesize);

	/**
	 * 获取巡检资源与资源变更情况
	 * 
	 * @param resId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPlanResWithChange(String resId);

	/**
	 * 无效资源查询
	 * 
	 * @param whereStr
	 * @return
	 */
	public List getPlanErrorDistanceRes(String whereStr, int offset,
			int pagesize);

	/**
	 * 查询待质检的资源（与计划关联）
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

	public Map<String, Integer> queryTotalAndHasDoneNum(String plan_id);

	public Map<String, Integer> queryHasDoneNum(String plan_id);

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
	 * 巡检完成情况
	 * 
	 * @return
	 */
	public List<InspectPlanResCountFromNew> countInspectPlanResNew(String countType,
			String areaId,String isPersonnel,String personnelDeptId,String year ,String month);
	
	/**
	 * 巡检完成情况   专业  带巡检钻取
	 * 
	 * @return
	 */
	public Map<String, Object> countInspectPlanResSpecialty(String countType,String areaId,
			String Specialty,String person,String year,String month,int firstIndex,int lastIndex);

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
	
	public Integer getPlanResCountByHql(String hql);
	/**
	 * 资源周期被修改时,将相关联的最近地一个原任务周期修改了
	 * @param planResId 资源id
	 * @param times  要修改成的时间数组
	 */
	public void changePlanResCycle(String planResId,String[] times,String cycle);
	
	/**
	 * 根据条件查询巡检任务列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getInspectPlanMainDetailList(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
