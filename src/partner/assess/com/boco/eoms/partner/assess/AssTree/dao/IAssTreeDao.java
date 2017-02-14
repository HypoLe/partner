/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.dao;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.base.dao.Dao;

/**
 * <p>
 * Title:后评估模板树Dao接口类
 * </p>
 * <p>
 * Description:后评估模板树Dao接口类
 * </p>
 * <p>
 * Date:Nov 23, 2010 1:53:07 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssTreeDao extends Dao {

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
	 * 保存树节点
	 * 
	 * @param assTreeNode
	 *            树节点
	 */
	public void saveTreeNode(AssTree assTreeNode);

	/**
	 * 根据节点id删除节点
	 * 
	 * @param assTreeNode
	 *            树节点
	 */
	public void removeTreeNode(AssTree assTreeNode);

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
	 * @param length
	 *            长度
	 * @return
	 */
	public String getMaxNodeId(String parentNodeId, int length);

	/**
	 * 根据节点id返回节点名称
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	public String id2Name(String nodeId);

	/**
	 * 删除某Id对应的节点以及所有下级节点
	 * 
	 * @param nodeId
	 */
	public void removeTreeNodeByNodeId(final String nodeId);

	/**
	 * 查询子节点
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
	 * 根据父节点Id查询汇总节点为Y的树节点
	 * 
	 * @param parentNodeId
	 *            父节点id
	 */
	public List listChildNodesByTotal(String parentNodeId) ;	
}
