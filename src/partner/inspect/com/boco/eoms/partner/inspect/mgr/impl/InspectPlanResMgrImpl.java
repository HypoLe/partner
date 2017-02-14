package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.inspect.dao.IInspectPlanDaoJdbc;
import com.boco.eoms.partner.inspect.dao.IInspectPlanResDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanItemCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;

/** 
 * Description: 巡检计划资源业务实现类 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 16, 2012 10:48:05 AM 
 */
public class InspectPlanResMgrImpl implements IInspectPlanResMgr {
	private IInspectPlanResDao inspectPlanResDao;
	private IInspectPlanDaoJdbc inspectPlanDaoJdbc;

	public IInspectPlanDaoJdbc getInspectPlanDaoJdbc() {
		return inspectPlanDaoJdbc;
	}

	public void setInspectPlanDaoJdbc(IInspectPlanDaoJdbc inspectPlanDaoJdbc) {
		this.inspectPlanDaoJdbc = inspectPlanDaoJdbc;
	}

	public IInspectPlanResDao getInspectPlanResDao() {
		return inspectPlanResDao;
	}

	public void setInspectPlanResDao(IInspectPlanResDao inspectPlanResDao) {
		this.inspectPlanResDao = inspectPlanResDao;
	}
	
	public InspectPlanRes get(Long id){
		return (InspectPlanRes)inspectPlanResDao.getObject(InspectPlanRes.class, id);
	}
	
	public void save(InspectPlanRes inspectPlanRes){
		inspectPlanResDao.saveObject(inspectPlanRes);
	}
	
	@SuppressWarnings("unchecked")
	public Map findInspectPlanResList(final Integer curPage, final Integer pageSize,final String whereStr){
		return inspectPlanResDao.findInspectPlanResList(curPage, pageSize, whereStr);
	}
	
	
	/**
	 * 资源查询  config表  2013-08-30
	 */
	@SuppressWarnings("unchecked")
	public Map findResourceList(final Integer curPage,final Integer pageSize, final String whereStr) {
		// TODO Auto-generated method stub
		return inspectPlanResDao.findResourceList(curPage, pageSize, whereStr); 
	}
	
	
	
	/**
	 * 根据HQL语句更新巡检资源关联的巡检计划
	 * @param planId
	 * @param where
	 */
	public void updateInspectPlanResByHql(String planId,String where){
		if(!StringUtils.isEmpty(where)){
			String hql = "update InspectPlanRes set planId='"+planId+"' where " + where;
			inspectPlanResDao.bulkUpdate(hql);
		}else{
			System.out.println("------警告：根据HQL语句更新巡检资源关联的巡检计划时更新条件为空------");
		}
	}
	
	/**
	 * 通过HQL更新巡检资源
	 * @param hql
	 */
	public void updateInspectPlanResByHql(String hql){
		inspectPlanResDao.bulkUpdate(hql);
	}
	
	/**
	 * 查询巡检计划已经关联的巡检资源数量
	 * @param planId
	 * @return
	 */
	public Integer getPlanResAssignCount(String planId){
		return inspectPlanResDao.getPlanResAssignCount(planId);
	}
	public Integer getExceptionResCount(String planId) {
		// TODO Auto-generated method stub
		return inspectPlanResDao.getExceptionResCount(planId);
	}
	/**
	 * 更新未细化巡检时间的巡检计划资源的巡检开始、结束时间
	 * @param planId
	 * @param planStartTime 巡检开始时间
	 * @param planEndTime 巡检结束时间
	 * @param currentMonth 当前月
	 */
	public void updateInspectPlanResPlanTime(String planId,String planStartTime,String planEndTime,String currentMonth){
		inspectPlanDaoJdbc.updateInspectPlanResPlanTime(planId, planStartTime, planEndTime, currentMonth);
	}
	
	/**
	 * 查询巡检资源与资源变更情况列表
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findPlanResWithChangeList(final Integer curPage, final Integer pageSize,final String whereStr){
		return inspectPlanResDao.findPlanResWithChangeList(curPage, pageSize, whereStr);
	}
	
	/**
	 * 查询巡检资源与资源变更情况列表
	 */
    public PageModel findPlanResWithChangeList(final String hql, final Object[] params, final int offset,final int pagesize){
    	return inspectPlanResDao.searchPaginated(hql, params, offset, pagesize);
    }
	
	/**
	 * 获取巡检资源与资源变更情况
	 * @param resId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPlanResWithChange(String resId){
		return inspectPlanResDao.getPlanResWithChange(resId);
	}
	public Map<String,Integer> queryTotalAndHasDoneNum(String plan_id){
		return inspectPlanResDao.queryTotalAndHasDoneNum(plan_id);
	}

	public Map<String, Integer> queryHasDoneNum(String plan_id) {
		// TODO Auto-generated method stub
		return inspectPlanResDao.queryHasDoneNum(plan_id);
	}

	/**
	 * 无效资源查询
	 * @param whereStr
	 * @return
	 */
	public List getPlanErrorDistanceRes(String whereStr,int offset,int pagesize){
		return inspectPlanDaoJdbc.getPlanErrorDistanceRes(whereStr,offset,pagesize);
	}
	
	/**
	 * 查询所有待质检的资源（与计划关联）
	 * @param whereStr
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Object> getAllWaitInspectPlanRes(String whereStr,int offset,int pagesize){
		return inspectPlanDaoJdbc.getAllWaitInspectPlanRes(whereStr, offset, pagesize);
	}
	
	/**
	 * 查询已质检的资源
	 * @param year
	 * @param month
	 * @param whereStr
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	public List<Object> getAllAleradyInspectPlanRes(int year,int month,String whereStr,int offset,int pagesize){
		return inspectPlanDaoJdbc.getAllAleradyInspectPlanRes(year, month, whereStr, offset, pagesize);
	}

//	@Override
	public List<InspectPlanResCountFrom> countInspectPlanRes(int countType,String isPersonnel,String personnelDeptId) {
		return inspectPlanDaoJdbc.countInspectPlanRes(countType,isPersonnel,personnelDeptId);
	}

//	@Override
	public List<InspectPlanResCountFrom> countInspectPlanRes(int countType,
			String areaId,String isPersonnel,String personnelDeptId) {
		return inspectPlanDaoJdbc.countInspectPlanRes(countType,areaId,isPersonnel,personnelDeptId);
	}
	
//	@Override
	public List<InspectPlanResCountFromNew> countInspectPlanResNew(String countType,
			String areaId,String isPersonnel,String personnelDeptId,String year ,String month) {
		return inspectPlanDaoJdbc.countInspectPlanResNew(countType,areaId,isPersonnel,personnelDeptId,year,month);
	}
	
//	@Override
	public Map<String,Object> countInspectPlanResSpecialty(String countType,
			String areaId ,String specialty,String person,String year ,String month,int firstIndex,int lastIndex) {
		Map<String,Object> map = new HashMap<String,Object>();
    	List<InspectPlanResCountFromSpecialty> r = new ArrayList<InspectPlanResCountFromSpecialty>();
    	List<InspectPlanResCountFromSpecialty> rlist = new ArrayList<InspectPlanResCountFromSpecialty>();
		r =  inspectPlanDaoJdbc.countInspectPlanResSpecialty(countType,areaId,specialty,person,year,month);
		
//      分页获取数据
        int size = r.size();
        int maxSize =0;
        int begin = firstIndex;
        //当取的值，为最大时
        if(size>=lastIndex){
        	maxSize =lastIndex;
        }else{
        	maxSize=size;
        }
       
        for(int index=begin;index<maxSize;index++){
    	   rlist.add(r.get(index));
        } 
        map.put("size",size);
       	        
        map.put("list",rlist);
        
        
        return map;
	}


//	@Override
	public List<InspectPlanResCountFrom> countInspectPlanResException(int countType,String isPersonnel,String personnelDeptId) {
		return inspectPlanDaoJdbc.countInspectPlanResException(countType,isPersonnel,personnelDeptId);
	}

//	@Override
	public List<InspectPlanResCountFrom> countInspectPlanResException(int countType,
			String areaId,String isPersonnel,String personnelDeptId) {
		return inspectPlanDaoJdbc.countInspectPlanResException(countType,areaId,isPersonnel,personnelDeptId);
	}
	public Integer getPlanResCountByHql(String hql){
		hql = "SELECT COUNT(*) FROM InspectPlanRes res " + hql;
		return inspectPlanResDao.getPlanResCountByHql(hql);
	}
	/**
	 * 资源周期被修改时,将相关联的最近地一个原任务周期修改了
	 * @param planResId 资源id
	 * @param times  要修改成的时间数组
	 */
	public void changePlanResCycle(String planResId,String[] times,String cycle){
		inspectPlanDaoJdbc.changePlanResCycle(planResId, times,cycle);
	}
	
	@SuppressWarnings("unchecked")
	public Map getInspectPlanMainDetailList(final Integer curPage, final Integer pageSize,final String whereStr){
		return inspectPlanResDao.getInspectPlanMainDetailList(curPage, pageSize, whereStr);
	}
	
}
