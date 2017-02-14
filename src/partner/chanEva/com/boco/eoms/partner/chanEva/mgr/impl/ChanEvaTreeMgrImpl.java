package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.chanEva.dao.IChanEvaTreeDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;

public class ChanEvaTreeMgrImpl implements IChanEvaTreeMgr {

	private IChanEvaTreeDao chanEvaTreeDao;

	public IChanEvaTreeDao getChanEvaTreeDao() {
		return chanEvaTreeDao;
	}

	public void setChanEvaTreeDao(IChanEvaTreeDao chanEvaTreeDao) {
		this.chanEvaTreeDao = chanEvaTreeDao;
	}

	public ChanEvaTree getTreeNode(String id) {
		return chanEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return chanEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ ChanEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public ChanEvaTree getTreeNodeByNodeId(String nodeId) {
		return chanEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = chanEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return chanEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return chanEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 删除当前节点及下级节点
		chanEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!ChanEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			ChanEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						ChanEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(ChanEvaTree chanEvaTreeNode) {
		chanEvaTreeDao.saveTreeNode(chanEvaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		ChanEvaTree node = chanEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		chanEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return chanEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(ChanEvaTree chanEvaTreeNode) {
		chanEvaTreeDao.removeTreeNode(chanEvaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return chanEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public ChanEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return chanEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", ChanEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			ChanEvaTree childNode = (ChanEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ ChanEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
}
