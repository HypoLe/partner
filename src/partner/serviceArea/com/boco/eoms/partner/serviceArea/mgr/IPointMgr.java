package com.boco.eoms.partner.serviceArea.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.model.Point;

/**
 * <p>
 * Title:数据点
 * </p>
 * <p>
 * Description:服务资源配置 数据点
 * </p>
 * <p>
 * Mon Nov 23 11:36:10 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
 public interface IPointMgr {
 
	/**
	 *
	 * 取数据点 列表
	 * @return 返回数据点列表
	 */
	public List getPoints();
    

	/**
	 *判断数据点是否重复
	 * 取数据点 列表
	 * @return 返回数据点列表
	 */
	public List getPointName(final String pointName);

	/**
	 *
	 * 二级联动菜单 加载地市 列表
	 * @return 返回抽查记录列表
	 */
	public List listCityOfArea(final String areaId,final String len);

	/**
	 *
	 * 二级联动菜单 加载县区 列表
	 * @return 返回抽查记录列表
	 */
	public List listCountryOfCity(final String cityId ,final String len);
	

	
	/**
	 * 根据主键查询数据点
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Point getPoint(final String id);
    
	/**
	 * 保存数据点
	 * @param point 数据点
	 */
	public void savePoint(Point point);
    
	/**
	 * 根据主键删除数据点
	 * @param id 主键
	 */
	public void removePoint(final String id);
    
	/**
	 * 根据条件分页查询数据点
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回数据点的分页列表
	 */
	public Map getPoints(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
}