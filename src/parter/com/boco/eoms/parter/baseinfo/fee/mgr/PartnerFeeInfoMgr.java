package com.boco.eoms.parter.baseinfo.fee.mgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
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
 public interface PartnerFeeInfoMgr {
 
	/**
	 *
	 * 取合作伙伴付费信息 列表
	 * @return 返回合作伙伴付费信息列表
	 */
	public List getPartnerFeeInfos();
    
	/**
	 * 根据主键查询合作伙伴付费信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PartnerFeeInfo getPartnerFeeInfo(final String id);
    
	/**
	 * 通过付款计划id查询 付费信息
	 * @param id
	 * @return
	 */
	public List getPartnerFeeInfoByPlanId(final String planId);
	
	/**
	 * 保存合作伙伴付费信息
	 * @param partnerFeeInfo 合作伙伴付费信息
	 */
	public void savePartnerFeeInfo(PartnerFeeInfo partnerFeeInfo);
    
	/**
	 * 根据主键删除合作伙伴付费信息
	 * @param id 主键
	 */
	public void removePartnerFeeInfo(final String id);
    
	/**
	 * 根据条件分页查询合作伙伴付费信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回合作伙伴付费信息的分页列表
	 */
	public Map getPartnerFeeInfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
    /**
	 * 根据收款单位查询 付费信息
	 * @param collectUnit
	 * @return
	 */
	public List getPartnerFeeInfosByCollectUnit(final String collectUnit,final String startDate,final String endDate);
	
	/**
	 * 根据付款单位查询 付费信息
	 * @param payUnit
	 * @return
	 */
	public List getPartnerFeeInfosByPayUnit(final String payUnit,final String startDate,final String endDate);
	
	/**
	 * 根据合同编号查询 付费信息
	 * @param compactNo
	 * @return
	 */
	public List getPartnerFeeInfosByCompactNo(final String compactNo,final String startDate,final String endDate);
	
	/**
	 * 收款单位收款金额统计
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCollectStatistics(final String collectUnit,final String startDate, final String endDate);
	
	/**
	 * 按付款单位付款金额统计
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeePayStatistics(final String payUnit,final String startDate, final String endDate);
	
	/**
	 * 按合同统计 付款金额
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCompactStatistics(final String compactNo,final String startDate, final String endDate);
}