package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.tranEva.dao.ITranEvaTreeDao;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTreeMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaTree;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;

public class TranEvaTreeMgrImpl implements ITranEvaTreeMgr {

	private ITranEvaTreeDao tranEvaTreeDao;

	public ITranEvaTreeDao getTranEvaTreeDao() {
		return tranEvaTreeDao;
	}

	public void setTranEvaTreeDao(ITranEvaTreeDao tranEvaTreeDao) {
		this.tranEvaTreeDao = tranEvaTreeDao;
	}

	public TranEvaTree getTreeNode(String id) {
		return tranEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return tranEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ TranEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public TranEvaTree getTreeNodeByNodeId(String nodeId) {
		return tranEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = tranEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return tranEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return tranEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 删除当前节点及下级节点
		tranEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!TranEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			TranEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						TranEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(TranEvaTree tranEvaTreeNode) {
		tranEvaTreeDao.saveTreeNode(tranEvaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		TranEvaTree node = tranEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		tranEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return tranEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(TranEvaTree tranEvaTreeNode) {
		tranEvaTreeDao.removeTreeNode(tranEvaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return tranEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public TranEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return tranEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", TranEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			TranEvaTree childNode = (TranEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ TranEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
}
