package com.boco.eoms.partner.assess.AssAutoCollection.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionResult;

/**
 * <p>
 * Title:采集结果
 * </p>
 * <p>
 * Description:采集结果
 * </p>
 * <p>
 * Thu Apr 07 14:49:20 CST 2011
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
 public interface AssCollectionResultMgr {
 
	/**
	 *
	 * 取采集结果 列表
	 * @return 返回采集结果列表
	 */
	public List getAssCollectionResults();
    
	/**
	 * 根据主键查询采集结果
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public AssCollectionResult getAssCollectionResult(final String id);
    
	/**
	 * 保存采集结果
	 * @param assCollectionResult 采集结果
	 */
	public void saveAssCollectionResult(AssCollectionResult assCollectionResult);
    
	/**
	 * 根据主键删除采集结果
	 * @param id 主键
	 */
	public void removeAssCollectionResult(final String id);
    
	/**
	 * 根据条件分页查询采集结果
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回采集结果的分页列表
	 */
	public Map getAssCollectionResults(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/**
	 * 按条件得到采集结果
	 */	
	public List getAssCollectionResults( final String whereStr ) ;
}