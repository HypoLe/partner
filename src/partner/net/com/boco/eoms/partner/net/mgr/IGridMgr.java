package com.boco.eoms.partner.net.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.net.model.Gride;

/**
 * <p>
 * Title:网格
 * </p>
 * <p>
 * Description:网格
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface IGridMgr {
 
	/**
	 *
	 * 取网格 列表
	 * @return 返回网格列表
	 */
	public List getGrids();
	
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
    *
    *根据条件取网格列表
    * @return 返回网格列表
    */    
    public List getGridsByWhere( final String whereStr );   	
    
	/**
	 * 根据主键查询网格
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Gride getGrid(final String id);
    
	/**
	 * 保存网格
	 * @param grid 网格
	 */
	public void saveGrid(Gride grid);
    
	/**
	 * 根据主键删除网格
	 * @param id 主键
	 */
	public void removeGrid(final String id);
    
	/**
	 * 根据条件分页查询网格
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回网格的分页列表
	 */
	public Map getGrids(final Integer curPage, final Integer pageSize,
			final String whereStr);

	/**
	 * 抽查基站巡检
	 * 根据条件分页查询站点信息管理
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回站点信息管理的分页列表
	 */
	public Map getCycSite(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
    /**
     * 根据GridName查网格
     * @param gridName 网格名称
     * @return list中为符合条件的参数
     */    
    public List getGridsByGridName( final String gridName ) ;
 	
    /**
     * 根据id批量删除
     * @param id 主键
     * 
     */
    public void removeGrids(final String[] ids);
    /**
     * 根据gridNumber查找网格
     * @param id 主键
     * 
     */
    public Gride getGridNumber(final String gridNumber);
}