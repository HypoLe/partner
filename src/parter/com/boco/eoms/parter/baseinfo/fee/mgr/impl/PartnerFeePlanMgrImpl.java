package com.boco.eoms.parter.baseinfo.fee.mgr.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeePlanDao;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeePlanMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;

/**
 * <p>
 * Title:合作伙伴付款计划
 * </p>
 * <p>
 * Description:合作伙伴付款计划
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeePlanMgrImpl implements PartnerFeePlanMgr {
 
	private PartnerFeePlanDao  partnerFeePlanDao;
 	
	public PartnerFeePlanDao getPartnerFeePlanDao() {
		return this.partnerFeePlanDao;
	}
 	
	public void setPartnerFeePlanDao(PartnerFeePlanDao partnerFeePlanDao) {
		this.partnerFeePlanDao = partnerFeePlanDao;
	}
 	
	/**
	 * 查询所有的付款计划
	 * @return
	 */
	public List getPartnerFeePlans1(){
		return partnerFeePlanDao.getPartnerFeePlans1();
	}
	
    public List getPartnerFeePlans() {
    	return partnerFeePlanDao.getPartnerFeePlans();
    }
    
    public PartnerFeePlan getPartnerFeePlan(final String id) {
    	return partnerFeePlanDao.getPartnerFeePlan(id);
    }
    
    public void savePartnerFeePlan(PartnerFeePlan partnerFeePlan) {
    	partnerFeePlanDao.savePartnerFeePlan(partnerFeePlan);
    }
    
    public void removePartnerFeePlan(final String id) {
    	partnerFeePlanDao.removePartnerFeePlan(id);
    }
    
    public Map getPartnerFeePlans(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerFeePlanDao.getPartnerFeePlans(curPage, pageSize, whereStr);
	}
    public Map getFeePlansByCreator(final Integer curPage, final Integer pageSize,final String userId,final String dept,final String payState) {
		String whereStr = "where 1=1 ";
    	if(userId!=null&&!"".equals(userId)){
    		whereStr +=" and partnerFeePlan.createUser = '"+userId+"' "; 
		}
		if(dept!=null&&!"".equals(dept)){
    		whereStr +=" and partnerFeePlan.createDept = '"+dept+"' ";
		}
		if(payState!=null&&!"".equals(payState)){
			whereStr +=" and partnerFeePlan.payState = '"+payState+"' "; 
		}
    	Map map =partnerFeePlanDao.getPartnerFeePlans(curPage, pageSize, whereStr);
    	return map;
	}
    /**
	 * 根据付款单位查询付款计划
	 * @param payUnit 付费单位
	 * @param startDate
	 * @param endDate
	 * @param payFlag 付费标识
	 * @return
	 */
	public List getFeePlantsByPayUnit(final String payUnit,final Timestamp time,final String payStatus){
		List list = null;
		list = this.partnerFeePlanDao.getFeePlantsByPayUnit(payUnit,time,payStatus);
		return list;
	}
    
    
    /**
	 * 统计付款计划数
	 * @param plan 付费计划
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getFeePlantStatistics(final String plan,final String startDate,final String endDate){
		List list = null;
		//informix实现
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate+ " 23:59:59" , "yyyy-MM-dd HH:mm:ss");
			list = this.partnerFeePlanDao.getFeePlantStatistics(plan,newStartDate,newEndDate);
		}
		//oracle
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			String newStartDate = startDate + " 00:00:00";
			String newEndDate = endDate + " 23:59:59";
			list = this.partnerFeePlanDao.getFeePlantStatistics(plan,newStartDate,newEndDate);
		} 
		return list;
	}
    
}