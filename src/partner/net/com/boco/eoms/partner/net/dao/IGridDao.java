package com.boco.eoms.partner.net.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
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
public interface IGridDao extends Dao {

    /**
    *
    *取网格列表
    * @return 返回网格列表
    */
    public List getGrids();
    /**
    *
    *根据条件取网格列表
    * @return 返回网格列表
    */    
    public List getGridsByWhere( final String whereStr );     
    /**
    *
    *二级联动菜单 加载地市 列表
    * @return 返回抽查记录列表
    */
    public List listCityOfArea(final String areaId,final String len);

    
    /**
    *
    *二级联动菜单 加载县区 列表
    * @return 返回抽查记录列表
    */
    public List listCountryOfCity(final String cityId,final String len);
    
    
    
    /**
    * 根据主键查询网格
    * @param id 主键
    * @return 返回某id的网格
    */
    public Gride getGrid(final String id);
    
    /**
    *
    * 保存网格    
    * @param grid 网格
    * 
    */
    public void saveGrid(Gride grid);
    
    /**
    * 根据id删除网格
    * @param id 主键
    * 
    */
    public void removeGrid(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getGrids(final Integer curPage, final Integer pageSize,
			final String whereStr);
 
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