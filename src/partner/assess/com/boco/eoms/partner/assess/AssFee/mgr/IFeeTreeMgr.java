package com.boco.eoms.partner.assess.AssFee.mgr;

import java.util.List;

import com.boco.eoms.partner.assess.AssFee.model.FeeTree;


/**
 * <p>
 * Title:考核线路信息树
 * </p>
 * <p>
 * Description:考核线路信息树
 * </p>
 * <p>
 * Tue Nov 23 08:36:47 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface IFeeTreeMgr {
    
    /**
	 * 根据主键查询考核线路信息树
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public FeeTree getFeeTree(final String id);
    
    /**
    * 保存考核线路信息树
    * @param feeTree 考核线路信息树
    */
    public void saveFeeTree(FeeTree feeTree);
			
	/**
	 * 根据节点id查询考核线路信息树
	 * 
	 * @param nodeId
	 *            节点id
	 * @return 返回某节点id的对象
	 */
	public FeeTree getFeeTreeByNodeId(final String nodeId);
	
	/**
	 * 根据节点id删除考核线路信息树
	 * 
	 * @param nodeId 
	 *            节点id
	 */
	public void removeFeeTreeByNodeId(final String nodeId);
	
	/**
	 * 查询下一级节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @return 下级节点列表
	 */
	public List getNextLevelFeeTrees(String parentNodeId);
	
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
	