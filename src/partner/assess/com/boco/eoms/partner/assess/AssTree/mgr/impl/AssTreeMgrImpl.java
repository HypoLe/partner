/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.assess.AssTree.dao.IAssTreeDao;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssConstants;

/**
 * <p>
 * Title:后评估树业务方法类
 * </p>
 * <p>
 * Description:后评估树业务方法类
 * </p>
 * <p>
 * Date:Nov 23, 2010 2:26:24 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssTreeMgrImpl implements IAssTreeMgr {

	protected IAssTreeDao assTreeDao;

	public IAssTreeDao getAssTreeDao() {
		return assTreeDao;
	}

	public void setAssTreeDao(IAssTreeDao assTreeDao) {
		this.assTreeDao = assTreeDao;
	}

	public AssTree getTreeNode(String id) {
		return assTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return assTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ AssConstants.NODEID_BETWEEN_LENGTH);
	}

	public AssTree getTreeNodeByNodeId(String nodeId) {
		return assTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = assTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return assTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return assTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		// 删除当前节点及下级节点
		assTreeDao.removeTreeNodeByNodeId(nodeId);
		if (!AssConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			AssTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (!isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						AssConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(AssTree assTreeNode) {
		assTreeDao.saveTreeNode(assTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		AssTree node = assTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		assTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return assTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(AssTree assTreeNode) {
		assTreeDao.removeTreeNode(assTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return assTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public AssTree getNodeByObjId(String parentNodeId, String objectId) {
		return assTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", AssConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			AssTree childNode = (AssTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ AssConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
	
	public List listChildNodesByTotal(String parentNodeId) {
		return assTreeDao.listChildNodesByTotal(parentNodeId);
	}
}
