package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.BackEstimate;

/**
 * <p>
 * Title:后评估指标体系
 * </p>
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p> 
 * Thu Oct 14 10:55:23 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface BackEstimateDao extends Dao {

    /**
    *
    *取后评估指标体系列表
    * @return 返回后评估指标体系列表
    */
    public List getBackEstimates();
    
    /**
    * 根据主键查询后评估指标体系
    * @param id 主键
    * @return 返回某id的后评估指标体系
    */
    public BackEstimate getBackEstimate(final String id);
    
    /**
    *
    * 保存后评估指标体系    
    * @param backEstimate 后评估指标体系
    * 
    */
    public void saveBackEstimate(BackEstimate backEstimate);
    
    /**
    * 根据id删除后评估指标体系
    * @param id 主键
    * 
    */
    public void removeBackEstimate(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getBackEstimates(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 按条件获取列表
     * @param whereStr where条件
     * @return 返回复合条件的后评估指标体系
     */	
	public List getBackEstimatesList( final String whereStr ) ;    
}