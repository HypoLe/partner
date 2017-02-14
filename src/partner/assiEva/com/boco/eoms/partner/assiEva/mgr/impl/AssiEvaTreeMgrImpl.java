package com.boco.eoms.partner.assiEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaTreeDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTreeMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaTree;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;

public class AssiEvaTreeMgrImpl implements IAssiEvaTreeMgr {

	private IAssiEvaTreeDao assiEvaTreeDao;

	public IAssiEvaTreeDao getAssiEvaTreeDao() {
		return assiEvaTreeDao;
	}

	public void setAssiEvaTreeDao(IAssiEvaTreeDao assiEvaTreeDao) {
		this.assiEvaTreeDao = assiEvaTreeDao;
	}

	public AssiEvaTree getTreeNode(String id) {
		return assiEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return assiEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ AssiEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public AssiEvaTree getTreeNodeByNodeId(String nodeId) {
		return assiEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = assiEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return assiEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return assiEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 删除当前节点及下级节点
		assiEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!AssiEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			AssiEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						AssiEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(AssiEvaTree assiEvaTreeNode) {
		assiEvaTreeDao.saveTreeNode(assiEvaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		AssiEvaTree node = assiEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		assiEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return assiEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(AssiEvaTree assiEvaTreeNode) {
		assiEvaTreeDao.removeTreeNode(assiEvaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return assiEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public AssiEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return assiEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", AssiEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			AssiEvaTree childNode = (AssiEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ AssiEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
}
