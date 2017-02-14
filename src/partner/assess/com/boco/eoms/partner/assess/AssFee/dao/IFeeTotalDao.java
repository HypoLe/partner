package com.boco.eoms.partner.assess.AssFee.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;

/**
 * <p>
 * Title:计算结果信息
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface IFeeTotalDao extends Dao {

    /**
    *
    *取计算结果信息列表
    * @return 返回计算结果信息列表
    */
    public List getFeeTotals();
    
    /**
    * 根据主键查询计算结果信息
    * @param id 主键
    * @return 返回某id的计算结果信息
    */
    public FeeTotal getFeeTotal(final String id);
    
    /**
    *
    * 保存计算结果信息    
    * @param feeTotal 计算结果信息
    * 
    */
    public void saveFeeTotal(FeeTotal feeTotal);
    
    /**
    * 根据id删除计算结果信息
    * @param id 主键
    * 
    */
    public void removeFeeTotal(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getFeeTotals(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 按条件得到计算结果信息
	 */	
	public List getFeeTotalsList( final String whereStr ) ;	
}