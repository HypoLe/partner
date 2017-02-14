package com.boco.eoms.summary.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.summary.model.TawzjMonth;

/**
 * <p>
 * Title:月工作总结
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Wed Jun 17 14:24:09 CST 2009
 * </p>
 * 
 * @author hanlu
 * @version 3.5
 * 
 */
 public interface TawzjMonthMgr {
 
	/**
	 *
	 * 取月工作总结 列表
	 * @return 返回月工作总结列表
	 */
	public List getTawzjMonths();
    
	/**
	 * 根据主键查询月工作总结
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public TawzjMonth getTawzjMonth(final String id);
    
	/**
	 * 保存月工作总结
	 * @param tawzjMonth 月工作总结
	 */
	public void saveTawzjMonth(TawzjMonth tawzjMonth);
    
	/**
	 * 根据主键删除月工作总结
	 * @param id 主键
	 */
	public void removeTawzjMonth(final String id);
    
	/**
	 * 根据条件分页查询月工作总结
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回月工作总结的分页列表
	 */
	public Map getTawzjMonths(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
}