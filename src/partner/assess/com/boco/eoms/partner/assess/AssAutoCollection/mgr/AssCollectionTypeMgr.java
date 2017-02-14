package com.boco.eoms.partner.assess.AssAutoCollection.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;

/**
 * <p>
 * Title:采集类型
 * </p>
 * <p>
 * Description:采集类型
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface AssCollectionTypeMgr {
 
	/**
	 *
	 * 取采集类型 列表
	 * @return 返回采集类型列表
	 */
	public List getAssCollectionTypes();
    
	/**
	 * 根据主键查询采集类型
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public AssCollectionType getAssCollectionType(final String id);
    
	/**
	 * 保存采集类型
	 * @param assCollectionType 采集类型
	 */
	public void saveAssCollectionType(AssCollectionType assCollectionType);
    
	/**
	 * 根据主键删除采集类型
	 * @param id 主键
	 */
	public void removeAssCollectionType(final String id);
    
	/**
	 * 根据条件分页查询采集类型
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回采集类型的分页列表
	 */
	public Map getAssCollectionTypes(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 根据节点Id查询采集类型
     * @param nodeId 对应树节点主键
     * @return 返回对应树节点主键的采集类型
     */  
	public AssCollectionType getAssCollectionTypeByNodeId(final String nodeId) ; 			
}