package com.boco.eoms.summary.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.summary.model.TawDutyWeek;

/**
 * <p>
 * Title:值周数据
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Tue Jun 16 17:25:37 CST 2009
 * </p>
 * 
 * @author LXM
 * @version 3.5
 * 
 */
 public interface TawDutyWeekMgr {
 
	/**
	 *
	 * 取值周数据 列表
	 * @return 返回值周数据列表
	 */
	public List getTawDutyWeeks();
    
	/**
	 * 根据主键查询值周数据
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public TawDutyWeek getTawDutyWeek(final String id);
    
	/**
	 * 保存值周数据
	 * @param tawDutyWeek 值周数据
	 */
	public void saveTawDutyWeek(TawDutyWeek tawDutyWeek);
    
	/**
	 * 根据主键删除值周数据
	 * @param id 主键
	 */
	public void removeTawDutyWeek(final String id);
    
	/**
	 * 根据条件分页查询值周数据
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回值周数据的分页列表
	 */
	public Map getTawDutyWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr,String title,String weekFlag,String userName,String startTime,String endTime);
	public Map getTawDutyWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr,String userid);
			
}