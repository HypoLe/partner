package com.boco.eoms.parter.baseinfo.fee.dao;

import com.boco.eoms.base.dao.Dao; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;

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
public interface PartnerFeeInfoDao extends Dao {

    /**
    *
    *取合作伙伴付费信息列表
    * @return 返回合作伙伴付费信息列表
    */
    public List getPartnerFeeInfos();
    
    /**
    * 根据主键查询合作伙伴付费信息
    * @param id 主键
    * @return 返回某id的合作伙伴付费信息
    */
    public PartnerFeeInfo getPartnerFeeInfo(final String id);
    
    /**
    *
    * 保存合作伙伴付费信息    
    * @param partnerFeeInfo 合作伙伴付费信息
    * 
    */
    public void savePartnerFeeInfo(PartnerFeeInfo partnerFeeInfo);
    
    /**
    * 根据id删除合作伙伴付费信息
    * @param id 主键
    * 
    */
    public void removePartnerFeeInfo(final String id);
    
    /**
	 * 通过付款计划id查询 付费信息
	 * @param id
	 * @return
	 */
	public List getPartnerFeeInfoByPlanId(final String planId);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPartnerFeeInfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
    /**
	 * 根据收款单位查询 付费信息 oracle
	 * @param collectUnit
	 * @return
	 */
	public List getPartnerFeeInfosByCollectUnit(final String collectUnit,final String startDate,final String endDate);
	
	/**
	 * 根据收款单位查询 付费信息 oracle
	 * @param payUnit
	 * @return
	 */
	public List getPartnerFeeInfosByPayUnit(final String payUnit,final String startDate,final String endDate);
	
	/**
	 * 根据收款单位查询 付费信息 oracle
	 * @param compactNo
	 * @return
	 */
	public List getPartnerFeeInfosByCompactNo(final String compactNo,final String startDate,final String endDate);
    
    /**
	 * 收款单位收款金额统计 oracle
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCollectStatistics(final String collectUnit,final String startDate, final String endDate);
	
	/**
	 * 按付款单位付款金额统计 oracle
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeePayStatistics( final String payUnit,final String startDate, final String endDate);
	
	/**
	 * 按合同统计 付款金额 oracle
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCompactStatistics( final String compactNo,final String startDate, final String endDate);
	
	//--------------------------------Informix DAO
	 /**
	 * 根据收款单位查询 付费信息 informix
	 * @param collectUnit
	 * @return
	 */
	public List getPartnerFeeInfosByCollectUnit(final String collectUnit,final Date startDate,final Date endDate);
	
	/**
	 * 根据收款单位查询 付费信息 informix
	 * @param payUnit
	 * @return
	 */
	public List getPartnerFeeInfosByPayUnit(final String payUnit,final Date startDate,final Date endDate);
	
	/**
	 * 根据收款单位查询 付费信息 informix
	 * @param compactNo
	 * @return
	 */
	public List getPartnerFeeInfosByCompactNo(final String compactNo,final Date startDate,final Date endDate);
    
    /**
	 * 收款单位收款金额统计 informix
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCollectStatistics(final String collectUnit,final Date startDate, final Date endDate);
	
	/**
	 * 按付款单位付款金额统计 informix
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeePayStatistics( final String payUnit,final Date startDate, final Date endDate);
	
	/**
	 * 按合同统计 付款金额 informix
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCompactStatistics( final String compactNo,final Date startDate, final Date Date);
}