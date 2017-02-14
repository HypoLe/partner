package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaTreeDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;

public class SiteEvaTreeMgrImpl implements ISiteEvaTreeMgr {

	private ISiteEvaTreeDao siteEvaTreeDao;

	public ISiteEvaTreeDao getSiteEvaTreeDao() { 
		return siteEvaTreeDao;
	}

	public void setSiteEvaTreeDao(ISiteEvaTreeDao siteEvaTreeDao) {
		this.siteEvaTreeDao = siteEvaTreeDao;
	}

	public SiteEvaTree getTreeNode(String id) {
		return siteEvaTreeDao.getTreeNode(id);
	}

	public String getMaxNodeId(String parentNodeId) {
		return siteEvaTreeDao.getMaxNodeId(parentNodeId, parentNodeId.length()
				+ SiteEvaConstants.NODEID_BETWEEN_LENGTH);
	}

	public SiteEvaTree getTreeNodeByNodeId(String nodeId) {
		return siteEvaTreeDao.getTreeNodeByNodeId(nodeId);
	}

	public boolean isHaveSameLevel(String parentNodeId, String nodeType) {
		boolean flag = false;
		List list = siteEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
		if (null != list && 0 < list.size()) {
			flag = true;
		}
		return flag;
	}

	public ArrayList listNextLevelNode(String parentNodeId, String nodeType) {
		return siteEvaTreeDao.listNextLevelNode(parentNodeId, nodeType);
	}
	
	
	public boolean isFirstKpiLevel(String nodeId){
		return siteEvaTreeDao.isFirstKpiLevel(nodeId);
	}

	public void removeTreeNodeByNodeId(final String nodeId) {
		// 删除当前节点及下级节点
		siteEvaTreeDao.removeTreeNodeByNodeId(nodeId);
		// 得到父节点
		String parentNodeId = getTreeNodeByNodeId(nodeId).getParentNodeId();
		if (!SiteEvaConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			SiteEvaTree parentNode = getTreeNodeByNodeId(parentNodeId);
			// 如果删除后没有同级节点则父节点为leaf
			if (isHaveSameLevel(parentNode.getNodeId(), "")) {
				updateParentNodeLeaf(parentNode.getNodeId(),
						SiteEvaConstants.NODE_LEAF);
			}
		}
	}

	public void saveTreeNode(SiteEvaTree siteEvaTreeNode) {
		siteEvaTreeDao.saveTreeNode(siteEvaTreeNode);
	}

	public void updateParentNodeLeaf(String nodeId, String leaf) {
		SiteEvaTree node = siteEvaTreeDao.getTreeNodeByNodeId(nodeId);
		node.setLeaf(leaf);
		siteEvaTreeDao.saveTreeNode(node);
	}

	public String id2Name(String nodeId) {
		return siteEvaTreeDao.id2Name(nodeId);
	}

	public void removeTreeNode(SiteEvaTree siteEvaTreeNode) {
		siteEvaTreeDao.removeTreeNode(siteEvaTreeNode);
	}

	public List listChildNodes(String parentNodeId, String nodeType, String leaf) {
		return siteEvaTreeDao.listChildNodes(parentNodeId, nodeType, leaf);
	}

	public SiteEvaTree getNodeByObjId(String parentNodeId, String objectId) {
		return siteEvaTreeDao.getNodeByObjId(parentNodeId, objectId);
	}

	public int getMaxLevelOfChildNode(String parentNodeId) {
		int maxLevel = 0;
		// 最大深度的节点一定是叶子节点
		List sonList = listChildNodes(parentNodeId, "", SiteEvaConstants.NODE_LEAF);
		String maxNodeId = parentNodeId;
		for (Iterator sonIt = sonList.iterator(); sonIt.hasNext();) {
			SiteEvaTree childNode = (SiteEvaTree) sonIt.next();
			if (childNode.getNodeId().length() > maxNodeId.length()) {
				maxNodeId = childNode.getNodeId();
			}
		}
		maxLevel = (maxNodeId.length() - parentNodeId.length())
				/ SiteEvaConstants.NODEID_BETWEEN_LENGTH;
		return maxLevel;
	}
}
