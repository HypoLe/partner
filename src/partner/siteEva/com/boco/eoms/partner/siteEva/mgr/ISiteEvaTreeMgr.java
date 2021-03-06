package com.boco.eoms.partner.siteEva.mgr;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.partner.siteEva.model.SiteEvaTree;

/**
 * <p>
 * Title:考核树业务方法类
 * </p>
 * <p>
 * Description:考核树业务方法类
 * </p>
 * <p>
 * Date:2008-11-20 上午09:56:10
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface ISiteEvaTreeMgr {

	/**
	 * 根据主键查询树节点
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public SiteEvaTree getTreeNode(String id);

	/**
	 * 根据节点id查询树节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	public SiteEvaTree getTreeNodeByNodeId(String nodeId);

	/**
	 * 删除树节点
	 * 
	 * @param siteEvaTreeNode
	 *            树节点
	 */
	public void removeTreeNode(SiteEvaTree siteEvaTreeNode);

	/**
	 * 保存树节点
	 * 
	 * @param siteEvaTreeNode
	 *            树节点
	 */
	public void saveTreeNode(SiteEvaTree siteEvaTreeNode);

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
	 *            节点类型（SiteEvaConstants.NODETYPE_KPI,NODETYPE_TEMPLATETYPE,NODETYPE_TEMPLATE）
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
	public SiteEvaTree getNodeByObjId(String parentNodeId, String objectId);

	/**
	 * 计算某节点下属节点层数（从1开始向下递增）
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @return
	 */
	public int getMaxLevelOfChildNode(String parentNodeId);
}
