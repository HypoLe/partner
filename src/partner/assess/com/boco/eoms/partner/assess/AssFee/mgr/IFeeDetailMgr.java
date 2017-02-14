package com.boco.eoms.partner.assess.AssFee.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssFee.model.FeeDetail;


/**
 * <p>
 * Title:光缆线路付费信息
 * </p>
 * <p>
 * Description:光缆线路付费信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface IFeeDetailMgr {
 
	/**
	 *
	 * 取光缆线路付费信息 列表
	 * @return 返回光缆线路付费信息列表
	 */
	public List getFeeDetails();
    
	/**
	 * 根据主键查询光缆线路付费信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public FeeDetail getFeeDetail(final String id);
    
	/**
	 * 保存光缆线路付费信息
	 * @param feeDetail 光缆线路付费信息
	 */
	public void saveFeeDetail(FeeDetail feeDetail);
    
	/**
	 * 根据主键删除光缆线路付费信息
	 * @param id 主键
	 */
	public void removeFeeDetail(final String id);
    
	/**
	 * 根据条件分页查询光缆线路付费信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回光缆线路付费信息的分页列表
	 */
	public Map getFeeDetails(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 按条件得到光缆线路付费信息
	 */	
	public List getFeeDetailList( final String whereStr );			
}