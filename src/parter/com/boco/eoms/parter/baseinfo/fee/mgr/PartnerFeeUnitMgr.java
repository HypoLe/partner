package com.boco.eoms.parter.baseinfo.fee.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;

/**
 * <p>
 * Title:合作伙伴费用单位
 * </p>
 * <p>
 * Description:合作伙伴费用单位
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
 public interface PartnerFeeUnitMgr {
 
	/**
	 *
	 * 取合作伙伴费用单位 列表
	 * @return 返回合作伙伴费用单位列表
	 */
	public List getPartnerFeeUnits();
    
	/**
	 * 根据主键查询合作伙伴费用单位
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PartnerFeeUnit getPartnerFeeUnit(final String id);
    
	/**
	 * 保存合作伙伴费用单位
	 * @param partnerFeeUnit 合作伙伴费用单位
	 */
	public void savePartnerFeeUnit(PartnerFeeUnit partnerFeeUnit);
    
	/**
	 * 根据主键删除合作伙伴费用单位
	 * @param id 主键
	 */
	public void removePartnerFeeUnit(final String id);
    
	/**
	 * 根据条件分页查询合作伙伴费用单位
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回合作伙伴费用单位的分页列表
	 */
	public Map getPartnerFeeUnits(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
}