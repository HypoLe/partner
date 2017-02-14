package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
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
public interface IStationDao extends Dao {

    /**
    *
    *取驻点列表
    * @return 返回驻点列表
    */
    public List getStations();
    
    /**
    * 根据主键查询驻点
    * @param id 主键
    * @return 返回某id的驻点
    */
    public Station getStation(final String id);
    
    /**
    *
    * 保存驻点    
    * @param station 驻点
    * 
    */
    public void saveStation(Station station);
    
    /**
    * 根据id删除驻点
    * @param id 主键
    * 
    */
    public void removeStation(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
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