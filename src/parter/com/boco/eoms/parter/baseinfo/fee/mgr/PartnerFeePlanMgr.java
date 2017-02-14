package com.boco.eoms.parter.baseinfo.fee.mgr;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;

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
 public interface PartnerFeePlanMgr {
 
	/**
	 *
	 * 取合作伙伴付款计划 列表
	 * @return 返回合作伙伴付款计划列表
	 */
	public List getPartnerFeePlans();
    
	/**
	 * 查询所有的付款计划
	 * @return
	 */
	public List getPartnerFeePlans1();
	
	/**
	 * 根据主键查询合作伙伴付款计划
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PartnerFeePlan getPartnerFeePlan(final String id);
    
	/**
	 * 保存合作伙伴付款计划
	 * @param partnerFeePlan 合作伙伴付款计划
	 */
	public void savePartnerFeePlan(PartnerFeePlan partnerFeePlan);
    
	/**
	 * 根据主键删除合作伙伴付款计划
	 * @param id 主键
	 */
	public void removePartnerFeePlan(final String id);
    
	/**
	 * 统计付款计划数
	 * @param 付款计划
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getFeePlantStatistics(final String plan,final String startDate,final String endDate);
	
	/**
	 * 根据条件分页查询合作伙伴付款计划
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回合作伙伴付款计划的分页列表
	 */
	public Map getPartnerFeePlans(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
	 * 根据付款单位查询付款计划
	 * @param payUnit 付费单位
	 * @param time
	 * @param payStatus 付费标识
	 * @return
	 */
	public List getFeePlantsByPayUnit(final String payUnit,final Timestamp time,final String payStatus);
    /**
	 * 根据建立人和状态查询付款计划
	 * @param userId 建立人
	 * @param deptId 建立部门(可2选1)
	 * @param payState 计划状态
	 * @return
	 */
    public Map getFeePlansByCreator(final Integer curPage, final Integer pageSize,final String userId,final String dept,final String payState);


}