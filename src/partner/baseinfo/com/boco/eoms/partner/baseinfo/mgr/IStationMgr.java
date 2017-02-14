package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.Station;

/**
 * <p>
 * Title:驻点
 * </p>
 * <p>
 * Description:驻点
 * </p>
 * <p>
 * Fri Dec 18 09:31:48 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface IStationMgr {
 
	/**
	 *
	 * 取驻点 列表
	 * @return 返回驻点列表
	 */
	public List getStations();
    
	/**
	 * 根据主键查询驻点
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Station getStation(final String id);
    
	/**
	 * 保存驻点
	 * @param station 驻点
	 */
	public void saveStation(Station station);
    
	/**
	 * 根据主键删除驻点
	 * @param id 主键
	 */
	public void removeStation(final String id);
    
	/**
	 * 根据条件分页查询驻点
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回驻点的分页列表
	 */
	public Map getStations(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
    *
    * 按条件取驻点列表
    * @param where where条件
    * @return 返回驻点列表
    */    
    public List getStations(final String where);		
}