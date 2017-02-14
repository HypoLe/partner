package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.tranEva.dao.ITranKpiConfigDao;
import com.boco.eoms.partner.tranEva.mgr.ITranKpiConfigMgr;
import com.boco.eoms.partner.tranEva.model.TranKpiConfig;
import com.boco.eoms.partner.tranEva.util.TranKpiConfigConstants;

/**
 * <p>
 * Title:指标打分配置
 * </p>
 * <p>
 * Description:指标打分配置
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class TranKpiConfigMgrImpl implements ITranKpiConfigMgr {
 
	private ITranKpiConfigDao  tranKpiConfigDao;
 	
	public ITranKpiConfigDao getTranKpiConfigDao() {
		return this.tranKpiConfigDao;
	}
 	
	public void setTranKpiConfigDao(ITranKpiConfigDao tranKpiConfigDao) {
		this.tranKpiConfigDao = tranKpiConfigDao;
	}
	
	public TranKpiConfig getTranKpiConfig(final String id) {
    	return tranKpiConfigDao.getTranKpiConfig(id);
    }
    
    public void saveTranKpiConfig(TranKpiConfig tranKpiConfig) {
    	boolean isNew = (null == tranKpiConfig.getId() || "".equals(tranKpiConfig.getId()));
    	if (isNew) {
    		// 生成新节点Id
    		String nodeId = getUsableNodeId(tranKpiConfig.getParentNodeId());
    		tranKpiConfig.setNodeId(nodeId);
    		tranKpiConfig.setLeaf(TranKpiConfigConstants.NODE_LEAF);
    		// 保存新节点
    		tranKpiConfigDao.saveTranKpiConfig(tranKpiConfig);
    		
    		// 如果父节点不是根结点则设置父节点为非叶子节点
    		if (!TranKpiConfigConstants.TREE_ROOT_ID.equals(tranKpiConfig.getParentNodeId())) {
    			updateNodeLeaf(tranKpiConfig.getParentNodeId(), TranKpiConfigConstants.NODE_NOTLEAF);
    		}
    	} else {
    		tranKpiConfigDao.saveTranKpiConfig(tranKpiConfig);
    	}
    }
    
	public TranKpiConfig getTranKpiConfigByNodeId(final String nodeId) {
		return tranKpiConfigDao.getTranKpiConfigByNodeId(nodeId);
	}
	
	public void removeTranKpiConfigByNodeId(final String nodeId) {
		TranKpiConfig tranKpiConfig = tranKpiConfigDao.getTranKpiConfigByNodeId(nodeId);
		// 获得父节点Id
		String parentNodeId = tranKpiConfig.getParentNodeId();
		tranKpiConfigDao.removeTranKpiConfigByNodeId(nodeId);
		
		// 删除节点后若父节点下已经没有其他子节点则将父节点设置为叶子节点
		if (!TranKpiConfigConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			if (!isHasNextLevel(parentNodeId)) {
				updateNodeLeaf(parentNodeId, TranKpiConfigConstants.NODE_LEAF);
			}
		}
	}
	
	public List getNextLevelTranKpiConfigs(String parentNodeId) {
		return tranKpiConfigDao.getNextLevelTranKpiConfigs(parentNodeId);
	}
	
	public String getUsableNodeId(String parentNodeId) {
		return tranKpiConfigDao.getUsableNodeId(parentNodeId, parentNodeId.length()
				+ TranKpiConfigConstants.NODEID_BETWEEN_LENGTH);
	}
	
	public boolean isHasNextLevel(String parentNodeId) {
		boolean flag = false;
		List list = tranKpiConfigDao.getNextLevelTranKpiConfigs(parentNodeId);
		if (list.iterator().hasNext()) {
			flag = true;
		}
		return flag;
	}
	
	public void updateNodeLeaf(String nodeId, String leaf) {
		TranKpiConfig tranKpiConfig = tranKpiConfigDao.getTranKpiConfigByNodeId(nodeId);
		tranKpiConfig.setLeaf(leaf);
		tranKpiConfigDao.saveTranKpiConfig(tranKpiConfig);
	}
	
	public List getChildNodes(String parentNodeId) {
		return tranKpiConfigDao.getChildNodes(parentNodeId);
	}
	
	public List getTranKpiConfigs( final String whereStr ) {
		return tranKpiConfigDao.getTranKpiConfigs(whereStr);
	}	
}