package com.boco.eoms.partner.assess.AssFee.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.assess.AssFee.dao.IFeeTreeDao;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTreeMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTree;
import com.boco.eoms.partner.assess.AssFee.util.FeeTreeConstants;


/**
 * <p>
 * Title:考核线路信息树
 * </p>
 * <p>
 * Description:考核线路信息树
 * </p>
 * <p>
 * Tue Nov 23 08:36:47 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeTreeMgrImpl implements IFeeTreeMgr {
 
	private IFeeTreeDao  feeTreeDao;
 	
	public IFeeTreeDao getFeeTreeDao() {
		return this.feeTreeDao;
	}
 	
	public void setFeeTreeDao(IFeeTreeDao feeTreeDao) {
		this.feeTreeDao = feeTreeDao;
	}
	
	public FeeTree getFeeTree(final String id) {
    	return feeTreeDao.getFeeTree(id);
    }
    
    public void saveFeeTree(FeeTree feeTree) {
    	boolean isNew = (null == feeTree.getId() || "".equals(feeTree.getId()));
    	if (isNew) {
    		// 生成新节点Id
    		String nodeId = getUsableNodeId(feeTree.getParentNodeId());
    		feeTree.setNodeId(nodeId);
    		feeTree.setLeaf(FeeTreeConstants.NODE_LEAF);
    		// 保存新节点
    		feeTreeDao.saveFeeTree(feeTree);
    		
    		// 如果父节点不是根结点则设置父节点为非叶子节点
    		if (!FeeTreeConstants.TREE_ROOT_ID.equals(feeTree.getParentNodeId())) {
    			updateNodeLeaf(feeTree.getParentNodeId(), FeeTreeConstants.NODE_NOTLEAF);
    		}
    	} else {
    		feeTreeDao.saveFeeTree(feeTree);
    	}
    }
    
	public FeeTree getFeeTreeByNodeId(final String nodeId) {
		return feeTreeDao.getFeeTreeByNodeId(nodeId);
	}
	
	public void removeFeeTreeByNodeId(final String nodeId) {
		FeeTree feeTree = feeTreeDao.getFeeTreeByNodeId(nodeId);
		// 获得父节点Id
		String parentNodeId = feeTree.getParentNodeId();
		feeTreeDao.removeFeeTreeByNodeId(nodeId);
		
		// 删除节点后若父节点下已经没有其他子节点则将父节点设置为叶子节点
		if (!FeeTreeConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			if (!isHasNextLevel(parentNodeId)) {
				updateNodeLeaf(parentNodeId, FeeTreeConstants.NODE_LEAF);
			}
		}
	}
	
	public List getNextLevelFeeTrees(String parentNodeId) {
		return feeTreeDao.getNextLevelFeeTrees(parentNodeId);
	}
	
	public String getUsableNodeId(String parentNodeId) {
		return feeTreeDao.getUsableNodeId(parentNodeId, parentNodeId.length()
				+ FeeTreeConstants.NODEID_BETWEEN_LENGTH);
	}
	
	public boolean isHasNextLevel(String parentNodeId) {
		boolean flag = false;
		List list = feeTreeDao.getNextLevelFeeTrees(parentNodeId);
		if (list.iterator().hasNext()) {
			flag = true;
		}
		return flag;
	}
	
	public void updateNodeLeaf(String nodeId, String leaf) {
		FeeTree feeTree = feeTreeDao.getFeeTreeByNodeId(nodeId);
		feeTree.setLeaf(leaf);
		feeTreeDao.saveFeeTree(feeTree);
	}
	
	public List getChildNodes(String parentNodeId) {
		return feeTreeDao.getChildNodes(parentNodeId);
	}
	
}