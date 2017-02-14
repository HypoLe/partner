package com.boco.eoms.parter.baseinfo.fee.mgr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeInfoDao;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeInfoMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeCollectStatistics;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeCompactStatistics;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePayStatistics;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;

/**
 * <p>
 * Title:合作伙伴付费信息
 * </p>
 * <p>
 * Description:合作伙伴付费信息
 * </p>
 * <p>
 * Wed Sep 09 11:22:35 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeeInfoMgrImpl implements PartnerFeeInfoMgr {
 
	private PartnerFeeInfoDao  partnerFeeInfoDao;
 	
	public PartnerFeeInfoDao getPartnerFeeInfoDao() {
		return this.partnerFeeInfoDao;
	}
 	
	public void setPartnerFeeInfoDao(PartnerFeeInfoDao partnerFeeInfoDao) {
		this.partnerFeeInfoDao = partnerFeeInfoDao;
	}
 	
    public List getPartnerFeeInfos() {
    	return partnerFeeInfoDao.getPartnerFeeInfos();
    }
    
    public PartnerFeeInfo getPartnerFeeInfo(final String id) {
    	return partnerFeeInfoDao.getPartnerFeeInfo(id);
    }
    
    /**
	 * 通过付款计划id查询 付费信息
	 * @param id
	 * @return
	 */
	public List getPartnerFeeInfoByPlanId(final String planId){
		return partnerFeeInfoDao.getPartnerFeeInfoByPlanId(planId);
	}
    
    public void savePartnerFeeInfo(PartnerFeeInfo partnerFeeInfo) {
    	partnerFeeInfoDao.savePartnerFeeInfo(partnerFeeInfo);
    }
    
    public void removePartnerFeeInfo(final String id) {
    	partnerFeeInfoDao.removePartnerFeeInfo(id);
    }
    
    public Map getPartnerFeeInfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerFeeInfoDao.getPartnerFeeInfos(curPage, pageSize, whereStr);
	}
	
    /**
	 * 根据收款单位查询 付费信息
	 * @param collectUnit
	 * @return
	 */
	public List getPartnerFeeInfosByCollectUnit(final String collectUnit,final String startDate,final String endDate){
		List list = null;
		//informix实现
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate, "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate , "yyyy-MM-dd HH:mm:ss");
			list = this.partnerFeeInfoDao.getPartnerFeeInfosByCollectUnit(collectUnit,newStartDate,newEndDate);
		}
		//oracle
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			list = this.partnerFeeInfoDao.getPartnerFeeInfosByCollectUnit(collectUnit,startDate,endDate);
		} 
		return list;
	}
	
	/**
	 * 根据收款单位查询 付费信息
	 * @param payUnit
	 * @return
	 */
	public List getPartnerFeeInfosByPayUnit(final String payUnit,final String startDate,final String endDate){
		List list = null;
		//informix实现
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate, "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate , "yyyy-MM-dd HH:mm:ss");
			list = this.partnerFeeInfoDao.getPartnerFeeInfosByPayUnit(payUnit,newStartDate,newEndDate);
		}
		//oracle
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			list = this.partnerFeeInfoDao.getPartnerFeeInfosByPayUnit(payUnit,startDate,endDate);
		} 
		return list;
	}
	
	/**
	 * 根据收款单位查询 付费信息
	 * @param compactNo
	 * @return
	 */
	public List getPartnerFeeInfosByCompactNo(final String compactNo,final String startDate,final String endDate){
		List list = null;
		//informix实现
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate , "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate , "yyyy-MM-dd HH:mm:ss");
			list = this.partnerFeeInfoDao.getPartnerFeeInfosByCompactNo(compactNo,newStartDate,newEndDate);
		}
		//oracle
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			list = this.partnerFeeInfoDao.getPartnerFeeInfosByCompactNo(compactNo,startDate,endDate);
		} 

		return partnerFeeInfoDao.getPartnerFeeInfosByCompactNo(compactNo,startDate,endDate);
	}
    
    /**
	 * 收款单位收款金额统计
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCollectStatistics( final String collectUnit,final String startDate, final String endDate){
		Map map = null;
		
		//informix实现
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
			map = this.partnerFeeInfoDao.getFeeCollectStatistics(collectUnit,newStartDate, newEndDate);
		}
		//oracle
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			String newStartDate = startDate + " 00:00:00";
			String newEndDate = endDate + " 23:59:59";
			map = this.partnerFeeInfoDao.getFeeCollectStatistics(collectUnit, newStartDate, newEndDate);
		} 

		List list = (List)map.get("result");
		List list_result = new ArrayList();
		int length = list.size();
		String start = startDate + " 00:00:00";
		String end = endDate + " 23:59:59";
		for (int i = 0; i < length; i++){
			PartnerFeeCollectStatistics partnerFeeCollectStatistics = new PartnerFeeCollectStatistics();
			Object [] objs = (Object[])list.get(i);
			System.out.println(StatisticMethod.objectToString(objs[0]));
			System.out.println(StatisticMethod.objectToString(objs[1]));
			partnerFeeCollectStatistics.setCollectUnit(StatisticMethod.objectToString(objs[0])); //收款单位
			partnerFeeCollectStatistics.setCountFee(StatisticMethod.objectToInteger(objs[1])); //总金额
			partnerFeeCollectStatistics.setBeginDate(start);//统计的开始时间
			partnerFeeCollectStatistics.setEndDate(end);//统计结束时间
			list_result.add(partnerFeeCollectStatistics);
		}
		map.put("result", list_result);
		return map;
	} 
	
	/**
	 * 按付款单位付款金额统计
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeePayStatistics( final String payUnit,final String startDate, final String endDate){
		Map map = null;
		
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
			map = this.partnerFeeInfoDao.getFeePayStatistics(payUnit,newStartDate, newEndDate);
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			String newStartDate = startDate + " 00:00:00";
			String newEndDate = endDate + " 23:59:59";
			map = this.partnerFeeInfoDao.getFeePayStatistics(payUnit,newStartDate, newEndDate);
		} 

		List list = (List)map.get("result");
		List list_result = new ArrayList();
		int length = list.size();
		String start = startDate + " 00:00:00";
		String end = endDate + " 23:59:59";
		for (int i = 0; i < length; i++){
			PartnerFeePayStatistics partnerFeePayStatistics = new PartnerFeePayStatistics();
			Object [] objs = (Object[])list.get(i);
			System.out.println(StatisticMethod.objectToString(objs[0]));
			System.out.println(StatisticMethod.objectToString(objs[1]));
			partnerFeePayStatistics.setPayUnit(StatisticMethod.objectToString(objs[0]));
			partnerFeePayStatistics.setCountFee(StatisticMethod.objectToInteger(objs[1])); 
			partnerFeePayStatistics.setBeginDate(start);//统计的开始时间
			partnerFeePayStatistics.setEndDate(end);//统计结束时间
			list_result.add(partnerFeePayStatistics);
		}
		map.put("result", list_result);
		return map;
	}
	
	/**
	 * 按合同统计 付款金额
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCompactStatistics( final String compactNo,final String startDate, final String endDate){
		Map map = null;
		
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			Date newStartDate = StatisticMethod.stringToDate( startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date newEndDate = StatisticMethod.stringToDate(endDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
			map = this.partnerFeeInfoDao.getFeeCompactStatistics(compactNo, newStartDate, newEndDate);
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			String newStartDate = startDate + " 00:00:00";
			String newEndDate = endDate + " 23:59:59";
			map = this.partnerFeeInfoDao.getFeeCompactStatistics(compactNo,newStartDate, newEndDate);
		} 

		List list = (List)map.get("result");
		List list_result = new ArrayList();
		int length = list.size();
		String start = startDate + " 00:00:00";
		String end = endDate + " 23:59:59";
		for (int i = 0; i < length; i++){
			PartnerFeeCompactStatistics partnerFeeCompacttatistics = new PartnerFeeCompactStatistics();
			Object [] objs = (Object[])list.get(i);
			System.out.println(StatisticMethod.objectToString(objs[0]));
			System.out.println(StatisticMethod.objectToString(objs[1]));
			partnerFeeCompacttatistics.setCompactNo(StatisticMethod.objectToString(objs[0]));
			partnerFeeCompacttatistics.setCountFee(StatisticMethod.objectToInteger(objs[1])); 
			partnerFeeCompacttatistics.setBeginDate(start);//统计的开始时间
			partnerFeeCompacttatistics.setEndDate(end);//统计结束时间
			list_result.add(partnerFeeCompacttatistics);
		}
		map.put("result", list_result);
		return map;
	}
}