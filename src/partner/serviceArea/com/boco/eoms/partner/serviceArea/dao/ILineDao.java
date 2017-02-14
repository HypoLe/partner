package com.boco.eoms.partner.serviceArea.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.serviceArea.model.Line;

/**
 * <p>
 * Title:线路
 * </p>
 * <p>
 * Description:服务资源配置 线路
 * </p>
 * <p>
 * Fri Nov 13 10:10:56 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public interface ILineDao extends Dao {

    /**
    *
    *取线路列表
    * @return 返回线路列表
    */
    public List getLines();
    
    /**
    * 根据主键查询线路
    * @param id 主键
    * @return 返回某id的线路
    */
    public Line getLine(final String id);
    
    public List getLineName(final String lineName);
        
    
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
    *
    *二级联动菜单 加载合作伙伴 列表 (根据地市、县区)
    * @return 返回抽查记录列表
    */
    public List listProviderOfCity(final String region,final String city);

    /**
    *
    *二级联动菜单 加载合作伙伴 列表 (根据地市)
    * @return 返回抽查记录列表
    */
    public List listProviderByRegion(final String region);
    
    /**
    *
    * 保存线路    
    * @param line 线路
    * 
    */
    public void saveLine(Line line);
    
    /**
    * 根据id删除线路
    * @param id 主键
    * 
    */
    public void removeLine(final String id);
    
    
    /**
    *
    * 判断段落名称是否已经存在 
    * @param line 线路
    * 
    */
    public Boolean isUnique(Line line);

    
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getLines(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}