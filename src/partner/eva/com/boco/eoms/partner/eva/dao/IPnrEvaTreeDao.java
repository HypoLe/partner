package com.boco.eoms.partner.eva.dao;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaTree;

/**
 * 
 * <p>
 * Title:考核树Dao接口
 * </p>
 * <p>
 * Description:考核树Dao接口
 * </p>
 * <p>
 * Date:2008-12-4 下午08:29:50
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IPnrEvaTreeDao extends Dao {

	/**
	 * 根据主键查询树节点
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public PnrEvaTree getTreeNode(String id);

	/**
	 * 根据节点id查询树节点
	 * 
	 * @param nodeId
	 *            节点id
	 * @return
	 */
	public PnrEvaTree getTreeNodeByNodeId(String nodeId);

	/**
	 * 保存树节点
	 * 
	 * @param evaTreeNode
	 *            树节点
	 */
	public void saveTreeNode(PnrEvaTree evaTreeNode);

	/**
	 * 根据节点id删除节点
	 * 
	 * @param evaTreeNode
	 *            树节点
	 */
	public void removeTreeNode(PnrEvaTree evaTreeNode);

	/**
	 * 查询下一级特定类型的节点，如果不分类型查找则nodeType传入一个空的字符串
	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @param nodeType
	 *            节点类型（EvaConstants.NODETYPE_KPI,NODETYPE_TEMPLATETYPE,NODETYPE_TEMPLATE）
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
	public PnrEvaTree getNodeByObjId(String parentNodeId, String objectId);


	/**
	 * 根据节点Id和地域信息得到下一级该地域节点集合
	 * 
	 * @param nodeId
	 *            节点Id
	 * @param area
	 *            当前用户的地域id
	 * @return
	 */
	public List listNextNodeByPNodeIdAndArea(String nodeId, String area);
	
	/**
	 * 根据节点Id和地域信息得到下一级该地域节点个性权重集合
	 * 
	 * @param nodeId
	 *            节点Id
	 * @param area
	 *            当前用户的地域id
	 * @return
	 */
	public List listWeightByPNodeIdAndArea(String nodeId, String area, String childType);
	
	/**
	 * 根据节点Id和地域信息得到下面某节点类型的节点(指标类型时返回所有,模板类型时返回下一级)
	 * 
	 * @param nodeId
	 *            节点Id
	 * @param area
	 *            当前用户的地域id
	 * @return
	 */
	public List listActNodesByPNodeIdAndArea(String nodeId, String area, String nodeType, String order);
	
	/**
	 * 跟据objectId得到PnrEvaTree
	 * 
	 * @paream objectId
	 * 
	 * @return
	 * 
	 * 贾智会 2009.11.11.17：20
	 */
	public PnrEvaTree getEvaTreeByObjceId(String objectId);
	}
