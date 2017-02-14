package com.boco.eoms.partner.eva.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.eva.model.PnrEvaTree;

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
public interface IPnrEvaTreeMgr {

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
	 * 删除树节点
	 * 
	 * @param evaTreeNode
	 *            树节点
	 */
	public void removeTreeNode(PnrEvaTree evaTreeNode);

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
	public PnrEvaTree getNodeByObjId(String parentNodeId, String objectId);

	/**
	 * 计算某节点下属节点层数（从1开始向下递增）
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @return
	 */
	public int getMaxLevelOfChildNode(String parentNodeId);

	/**
	 * 计算某地域某节点下属节点层数（从1开始向下递增）
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @param areaId
	 *            地域Id
	 * @return
	 */
	public int getMaxLevelOfChildNodeByArea(String parentNodeId,String areaId);

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
	 * 根据按节点Id反排序tree集合得到展现表格用的占行数
	 * 
	 * @param nodeList
	 *            tree反排序节点集合	 * 
	 * @param parentNodeId
	 *            父节点id
	 * @return
	 */
	public Map getRowspanForNodeList(String parentNodeId, int length,List nodeList, String order);

	/**
	 * 根据节点Id和地域信息得到节点个性权重集合
	 * 
	 * @param nodeId
	 *            节点Id
	 * @param area
	 *            当前用户的地域id
	 * @param childType
	 *            查询返回子节点集合是下一级还是全部
	 * @return
	 */
	public Map listWeightByPNodeIdAndArea(String nodeId, String area, String childType);
	
	/**
	 * 根据节点Id和地域信息得到下面某节点类型的节点(指标类型时返回所有,模板类型时返回下一级)
	 * 
	 * @param nodeId
	 *            节点Id
	 * @param area
	 *            当前用户的地域id
	 * @param nodeType
	 *            返回节点类型
	 * @return
	 */
	public List listActNodesByPNodeIdAndArea(String nodeId, String area, String nodeType, String order);

	
	/**
	 * 计算某节点目前可调整权重的最大值
	 * 
	 * @param parentNodeId
	 *            父节点Id
	 * @param areaId
	 *            地域id
	 * @return
	 */
	public float getMaxWt(String parentNodeId, String areaId,String nodeType);
	
	/**
	 * 计算某节点目前可调整权重的最小值
	 * 
	 * @param nodeId
	 *            当前节点Id
	 * @param areaId
	 *            地域id
	 * @return
	 */
	public float getMinWt(String nodeId, String areaId);
	
	/**
	 * 根据子节点id和要找到节点的节点类型向上一直找到最下一层该类型的节点
	 * 
	 * @param nodeId
	 *            当前节点Id
	 * @param nodeType
	 *            要找到节点的节点类型
	 * @return
	 */
	
	public PnrEvaTree getLeafNodeBySubNodeIdAndNodeType(String nodeId, String nodeType);
	
	/**
	 * 跟据objectId得到PnrEvaTree
	 * 
	 * @paream objectId
	 * 
	 * @return
	 * 
	 * 贾智会 2009.11.11.17：25
	 */
	public PnrEvaTree getEvaTreeByObjceId(String objectId);
	}
