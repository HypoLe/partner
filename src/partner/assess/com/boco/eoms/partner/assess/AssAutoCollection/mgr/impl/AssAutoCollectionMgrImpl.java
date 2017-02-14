package com.boco.eoms.partner.assess.AssAutoCollection.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssAutoCollectionDao;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssAutoCollectionMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssAutoCollection;
import com.boco.eoms.partner.assess.AssAutoCollection.util.AssAutoCollectionConstants;

/**
 * <p>
 * Title:kpi自动采集
 * </p>
 * <p>
 * Description:kpi自动采集
 * </p>
 * <p>
 * Tue Mar 29 17:39:54 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssAutoCollectionMgrImpl implements AssAutoCollectionMgr {
 
	private AssAutoCollectionDao  assAutoCollectionDao;
 	
	public AssAutoCollectionDao getAssAutoCollectionDao() {
		return this.assAutoCollectionDao;
	}
 	
	public void setAssAutoCollectionDao(AssAutoCollectionDao assAutoCollectionDao) {
		this.assAutoCollectionDao = assAutoCollectionDao;
	}
	
	public AssAutoCollection getAssAutoCollection(final String id) {
    	return assAutoCollectionDao.getAssAutoCollection(id);
    }
    
    public void saveAssAutoCollection(AssAutoCollection assAutoCollection) {
    	boolean isNew = (null == assAutoCollection.getId() || "".equals(assAutoCollection.getId()));
    	if (isNew) {
    		// 生成新节点Id
    		String nodeId = getUsableNodeId(assAutoCollection.getParentNodeId());
    		assAutoCollection.setNodeId(nodeId);
    		assAutoCollection.setLeaf(AssAutoCollectionConstants.NODE_LEAF);
    		// 保存新节点
    		assAutoCollectionDao.saveAssAutoCollection(assAutoCollection);
    		
    		// 如果父节点不是根结点则设置父节点为非叶子节点
    		if (!AssAutoCollectionConstants.TREE_ROOT_ID.equals(assAutoCollection.getParentNodeId())) {
    			updateNodeLeaf(assAutoCollection.getParentNodeId(), AssAutoCollectionConstants.NODE_NOTLEAF);
    		}
    	} else {
    		assAutoCollectionDao.saveAssAutoCollection(assAutoCollection);
    	}
    }
    
	public AssAutoCollection getAssAutoCollectionByNodeId(final String nodeId) {
		return assAutoCollectionDao.getAssAutoCollectionByNodeId(nodeId);
	}
	
	public void removeAssAutoCollectionByNodeId(final String nodeId) {
		AssAutoCollection assAutoCollection = assAutoCollectionDao.getAssAutoCollectionByNodeId(nodeId);
		// 获得父节点Id
		String parentNodeId = assAutoCollection.getParentNodeId();
		assAutoCollectionDao.removeAssAutoCollectionByNodeId(nodeId);
		
		// 删除节点后若父节点下已经没有其他子节点则将父节点设置为叶子节点
		if (!AssAutoCollectionConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			if (!isHasNextLevel(parentNodeId)) {
				updateNodeLeaf(parentNodeId, AssAutoCollectionConstants.NODE_LEAF);
			}
		}
	}
	
	public List getNextLevelAssAutoCollections(String parentNodeId) {
		return assAutoCollectionDao.getNextLevelAssAutoCollections(parentNodeId);
	}
	
	public String getUsableNodeId(String parentNodeId) {
		return assAutoCollectionDao.getUsableNodeId(parentNodeId, parentNodeId.length()
				+ AssAutoCollectionConstants.NODEID_BETWEEN_LENGTH);
	}
	
	public boolean isHasNextLevel(String parentNodeId) {
		boolean flag = false;
		List list = assAutoCollectionDao.getNextLevelAssAutoCollections(parentNodeId);
		if (list.iterator().hasNext()) {
			flag = true;
		}
		return flag;
	}
	
	public void updateNodeLeaf(String nodeId, String leaf) {
		AssAutoCollection assAutoCollection = assAutoCollectionDao.getAssAutoCollectionByNodeId(nodeId);
		assAutoCollection.setLeaf(leaf);
		assAutoCollectionDao.saveAssAutoCollection(assAutoCollection);
	}
	
	public List getChildNodes(String parentNodeId) {
		return assAutoCollectionDao.getChildNodes(parentNodeId);
	}
	
}