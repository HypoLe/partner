package com.boco.eoms.parter.baseinfo.fee.dao;

import com.boco.eoms.base.dao.Dao;

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
public interface PartnerFeePlanDao extends Dao {

    /**
    *
    *取合作伙伴付款计划列表
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
    * @return 返回某id的合作伙伴付款计划
    */
    public PartnerFeePlan getPartnerFeePlan(final String id);
    
    /**
    *
    * 保存合作伙伴付款计划    
    * @param partnerFeePlan 合作伙伴付款计划
    * 
    */
    public void savePartnerFeePlan(PartnerFeePlan partnerFeePlan);
    
    /**
    * 根据id删除合作伙伴付款计划
    * @param id 主键
    * 
    */
    public void removePartnerFeePlan(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPartnerFeePlans(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
    /**
     * 统计付款计划数 oracle
     * @param plan 付费计划
     * @param startDate
     * @param endDate
     * @return
     */
	public List getFeePlantStatistics(final String plan,final String startDate,final String endDate);
	
	/**
	 * 统计付款计划数 informix
	 * @param plan 付费计划
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getFeePlantStatistics(final String plan,final Date startDate,final Date endDate);
	/**
	 * 根据付款单位查询付款计划
	 * @param payUnit 付费单位
	 * @param time
	 * @param payStatus 付费标识
	 * @return
	 */
	public List getFeePlantsByPayUnit(final String payUnit,final Timestamp time,final String payStatus);

}