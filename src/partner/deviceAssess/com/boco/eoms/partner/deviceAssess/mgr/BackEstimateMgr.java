package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.BackEstimate;

/**
 * <p>
 * Title:后评估指标体系
 * </p>
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p>
 * Thu Oct 14 10:55:23 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface BackEstimateMgr {
 
	/**
	 *
	 * 取后评估指标体系 列表
	 * @return 返回后评估指标体系列表
	 */
	public List getBackEstimates();
    
	/**
	 * 根据主键查询后评估指标体系
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public BackEstimate getBackEstimate(final String id);
    
	/**
	 * 保存后评估指标体系
	 * @param backEstimate 后评估指标体系
	 */
	public void saveBackEstimate(BackEstimate backEstimate);
    
	/**
	 * 根据主键删除后评估指标体系
	 * @param id 主键
	 */
	public void removeBackEstimate(final String id);
    
	/**
	 * 根据条件分页查询后评估指标体系
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回后评估指标体系的分页列表
	 */
	public Map getBackEstimates(final Integer curPage, final Integer pageSize,
			final String whereStr);

    /**
     * 按条件获取列表
     * @param whereStr where条件
     * @return 返回复合条件的后评估指标体系
     */	
	public List getBackEstimatesList( final String whereStr ) ;  	
}