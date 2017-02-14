package com.boco.eoms.assEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.assEva.dao.IAssEvaTreeDao;
import com.boco.eoms.assEva.mgr.IAssEvaTreeMgr;
import com.boco.eoms.assEva.model.AssEvaTree;
import com.boco.eoms.assEva.util.AssEvaConstants;

public class AssEvaTreeMgrImpl implements IAssEvaTreeMgr {

	private IAssEvaTreeDao assEvaTreeDao;

	public IAssEvaTreeDao getAssEvaTreeDao() {
		return assEvaTreeDao;
	}

	public void setAssEvaTreeDao(IAssEvaTreeDao assEvaTreeDao) {
		this.assEvaTreeDao = assEvaTreeDao;
	}

	public AssEvaTree getTreeNode(String id) {
		return assEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return assEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ AssEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public AssEvaTree getTreeNodeByNodeId(String nodeId) {
		return assEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = assEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return assEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return assEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 删除当前节点及下级节点
		assEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!AssEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			AssEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						AssEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(AssEvaTree assEvaTreeNode) {
		assEvaTreeDao.saveTreeNode(assEvaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		AssEvaTree node = assEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		assEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return assEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(AssEvaTree assEvaTreeNode) {
		assEvaTreeDao.removeTreeNode(assEvaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return assEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public AssEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return assEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", AssEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			AssEvaTree childNode = (AssEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ AssEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
}
