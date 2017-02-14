package com.boco.eoms.netresource.point.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.netresource.point.model.Points;

/**
 * <p>
 * Title:标点信息管理
 * </p>
 * <p>
 * Description:标点信息管理
 * </p>
 * <p>
 * Thu Feb 16 18:22:16 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public interface IPointsDao extends Dao {

    /**
     *
     *取标点信息管理列表
     * @return 返回标点信息管理列表
     */
    public List getPointss();
    
    /**
     * 根据条件查询
     */
    public List getPointsByProperty(String whereStr);
    
    /**
     * 根据主键查询标点信息管理
     * @param id 主键
     * @return 返回某id的标点信息管理
     */
    public Points getPoints(final String id);
    
    /**
     *
     * 保存标点信息管理    
     * @param points 标点信息管理
     * 
     */
    public void savePoints(Points points);
    
    /**
     * 根据id删除标点信息管理
     * @param id 主键
     * 
     */
    public void removePoints(final String id);
    
    /**
     * 分页取列表
     * @param curPage 当前页
     * @param pageSize 每页显示条数
     * @param whereStr where条件
     * @return map中total为条数,result(list) curPage页的记录
     */
    public Map getPointss(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}