package com.boco.eoms.partner.assess.AssFee.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assess.AssFee.model.FeeDetail;

/**
 * <p>
 * Title:光缆线路付费信息
 * </p>
 * <p>
 * Description:光缆线路付费信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface IFeeDetailDao extends Dao {

    /**
    *
    *取光缆线路付费信息列表
    * @return 返回光缆线路付费信息列表
    */
    public List getFeeDetails();
    
    /**
    * 根据主键查询光缆线路付费信息
    * @param id 主键
    * @return 返回某id的光缆线路付费信息
    */
    public FeeDetail getFeeDetail(final String id);
    
    /**
    *
    * 保存光缆线路付费信息    
    * @param feeDetail 光缆线路付费信息
    * 
    */
    public void saveFeeDetail(FeeDetail feeDetail);
    
    /**
    * 根据id删除光缆线路付费信息
    * @param id 主键
    * 
    */
    public void removeFeeDetail(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getFeeDetails(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 按条件得到光缆线路付费信息
	 */	
	public List getFeeDetailList( final String whereStr ) ;
}