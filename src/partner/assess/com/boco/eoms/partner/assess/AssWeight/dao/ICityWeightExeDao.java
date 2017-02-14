package com.boco.eoms.partner.assess.AssWeight.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeightExe;

/**
 * <p>
 * Title:地市配置权重
 * </p>
 * <p>
 * Description:地市配置权重
 * </p>
 * <p>
 * Tue Feb 22 15:42:16 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface ICityWeightExeDao extends Dao {

    /**
    *
    *取地市配置权重列表
    * @return 返回地市配置权重列表
    */
    public List getCityWeightExes();
    
    /**
    * 根据主键查询地市配置权重
    * @param id 主键
    * @return 返回某id的地市配置权重
    */
    public CityWeightExe getCityWeightExe(final String id);
    
    /**
    *
    * 保存地市配置权重    
    * @param cityWeightExe 地市配置权重
    * 
    */
    public void saveCityWeightExe(CityWeightExe cityWeightExe);
    
    /**
    * 根据id删除地市配置权重
    * @param id 主键
    * 
    */
    public void removeCityWeightExe(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getCityWeightExes(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/**
	 * 按条件取地市配置权重
	 * 
	 * @param where 条件
	 *            
	 */
	public List getCityWeightExes(final String where) ;
}