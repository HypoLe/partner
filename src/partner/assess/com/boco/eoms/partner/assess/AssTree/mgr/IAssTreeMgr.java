/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.mgr;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.partner.assess.AssTree.model.AssTree;

/**
 * <p>
 * Title: 后评估树业务方法类
 * </p>
 * <p>
 * Description: 后评估树业务方法类
 * </p>
 * <p>
 * Date:Nov 23, 2010 2:20:59 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssTreeMgr {

	/**
	 * 根据主键查询树节点
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public AssTree getTreeNode(String id);

	/**
	 * 根据节点id查询树节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	public AssTree getTreeNodeByNodeId(String nodeId);

	/**
	 * 删除树节点
	 * 
	 * @param assTreeNode
	 *            树节点
	 */
	public void removeTreeNode(AssTree assTreeNode);

	/**
	 * 保存树节点
	 * 
	 * @param assTreeNode
	 *            树节点
	 */
	public void saveTreeNode(AssTree assTreeNode);

	/**
	 * 根据节点id删除节点
	 * 
	 * @param nodeId
	 *            节点id
	 */
	public void removeTreeNodeByNodeId(final String nodeId);

	/**
	 * 查询下一级特定类型的节点，如果不分类型查找则nodeType传入一个空的字符串
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @param nodeType
	 *            节点类型（AssConstants.NODETYPE_KPI,NODETYPE_TEMPLATETYPE,NODETYPE_TEMPLATE）
	 * @return
	 */
	public ArrayList listNextLevelNode(String parentNodeId, String nodeType);

	/**
	 * 判断所给nodeId对应记录是否为第一层KPI
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	public boolean isFirstKpiLevel(String nodeId);
	/**
	 * 查询最大的节点id
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @return
	 */
	public String getMaxNodeId(String parentNodeId);

	/**
	 * 判断是否有相同级别的节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @param nodeType
	 *            节点类型，不指定节点类型nodeType传入空字符串
	 * @return
	 */
	public boolean isHaveSameLevel(String parentNodeId, String nodeType);

	/**
	 * 更新某节点的叶子节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @param leaf
	 *            叶子节点标志
	 */
	public void updateParentNodeLeaf(String nodeId, String leaf);

	/**
	 * 根据节点id返回节点名称
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	public String id2Name(String nodeId);

	/**
	 * 查询子节点（按nodeId排序）
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @param nodeType
	 *            节点类型（不区分节点类型请传入空字符串）
	 * @param leaf
	 *            叶子节点标志(不区分是否叶子节点请传入空字符串参数）
	 */
	public List listChildNodes(String parentNodeId, String nodeType, String leaf);

	/**
	 * 根据父节点Id和对象Id查询节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @param objectId
	 *            对象Id
	 */
	public AssTree getNodeByObjId(String parentNodeId, String objectId);

	/**
	 * 计算某节点下属节点层数（从1开始向下递增）
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @return
	 */
	public int getMaxLevelOfChildNode(String parentNodeId);
	
	/**
	 * 根据父节点Id查询汇总节点为Y的树节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 */
	public List listChildNodesByTotal(String parentNodeId) ;		
}
