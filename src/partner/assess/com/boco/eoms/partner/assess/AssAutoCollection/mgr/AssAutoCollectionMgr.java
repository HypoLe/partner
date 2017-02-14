package com.boco.eoms.partner.assess.AssAutoCollection.mgr;

import java.util.List;

import com.boco.eoms.partner.assess.AssAutoCollection.model.AssAutoCollection;

/**
 * <p>
 * Title:kpi自动采集
 * </p>
 * <p>
 * Description:kpi自动采集
 * </p>
 * <p>
 * Tue Mar 29 17:39:54 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface AssAutoCollectionMgr {
    
    /**
	 * 根据主键查询kpi自动采集
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public AssAutoCollection getAssAutoCollection(final String id);
    
    /**
    * 保存kpi自动采集
    * @param assAutoCollection kpi自动采集
    */
    public void saveAssAutoCollection(AssAutoCollection assAutoCollection);
			
	/**
	 * 根据节点id查询kpi自动采集
	 * 
	 * @param nodeId
	 *            节点id
	 * @return 返回某节点id的对象
	 */
	public AssAutoCollection getAssAutoCollectionByNodeId(final String nodeId);
	
	/**
	 * 根据节点id删除kpi自动采集
	 * 
	 * @param nodeId
	 *            节点id
	 */
	public void removeAssAutoCollectionByNodeId(final String nodeId);
	
	/**
	 * 查询下一级节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @return 下级节点列表
	 */
	public List getNextLevelAssAutoCollections(String parentNodeId);
	
	/**
	 * 生成一个可用的节点id
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @return
	 */
	public String getUsableNodeId(String parentNodeId);
	
	/**
	 * 判断是否有下级节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @return 有下级节点返回true，无下级节点返回false
	 */
	public boolean isHasNextLevel(String parentNodeId);
	
	/**
	 * 更新某节点为叶子节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @param leaf
	 *            叶子节点标志
	 */
	public void updateNodeLeaf(String nodeId, String leaf);
	
	/**
	 * 查询所有子节点（按nodeId排序）
	 * 
	 * @param parentNodeId
	 *            父节点id
	 */
	public List getChildNodes(String parentNodeId);

}
	