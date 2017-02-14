package com.boco.eoms.partner.assess.AssFee.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;


/**
 * <p>
 * Title:计算结果信息
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface IFeeTotalMgr {
 
	/**
	 *
	 * 取计算结果信息 列表
	 * @return 返回计算结果信息列表
	 */
	public List getFeeTotals();
    
	/**
	 * 根据主键查询计算结果信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public FeeTotal getFeeTotal(final String id);
    
	/**
	 * 保存计算结果信息
	 * @param feeTotal 计算结果信息
	 */
	public void saveFeeTotal(FeeTotal feeTotal);
    
	/**
	 * 根据主键删除计算结果信息
	 * @param id 主键
	 */
	public void removeFeeTotal(final String id);
    
	/**
	 * 根据条件分页查询计算结果信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回计算结果信息的分页列表
	 */
	public Map getFeeTotals(final Integer curPage, final Integer pageSize,
			final String whereStr);

	/**
	 * 按条件得到计算结果信息
	 */	
	public List getFeeTotalsList( final String whereStr ) ;	
	
	/**
	 * 按地市，年份得到费用
	 */	
	public FeeTotal getFeeTotalByArea( final String areaId, final String year ) ;		
	
}