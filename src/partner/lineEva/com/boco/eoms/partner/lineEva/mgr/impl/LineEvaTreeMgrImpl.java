package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.lineEva.dao.ILineEvaTreeDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTreeMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaTree;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;

public class LineEvaTreeMgrImpl implements ILineEvaTreeMgr {

	private ILineEvaTreeDao lineEvaTreeDao;

	public ILineEvaTreeDao getLineEvaTreeDao() {
		return lineEvaTreeDao;
	}

	public void setLineEvaTreeDao(ILineEvaTreeDao lineEvaTreeDao) {
		this.lineEvaTreeDao = lineEvaTreeDao;
	}

	public LineEvaTree getTreeNode(String id) {
		return lineEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return lineEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ LineEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public LineEvaTree getTreeNodeByNodeId(String nodeId) {
		return lineEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = lineEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return lineEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return lineEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 删除当前节点及下级节点
		lineEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!LineEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			LineEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						LineEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(LineEvaTree lineEvaTreeNode) {
		lineEvaTreeDao.saveTreeNode(lineEvaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		LineEvaTree node = lineEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		lineEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return lineEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(LineEvaTree lineEvaTreeNode) {
		lineEvaTreeDao.removeTreeNode(lineEvaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return lineEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public LineEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return lineEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", LineEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			LineEvaTree childNode = (LineEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ LineEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
}
