package com.boco.eoms.assEva.mgr.impl;

import java.util.List;

import com.boco.eoms.assEva.dao.IAssKpiConfigDao;
import com.boco.eoms.assEva.mgr.IAssKpiConfigMgr;
import com.boco.eoms.assEva.model.AssKpiConfig;
import com.boco.eoms.assEva.util.AssKpiConfigConstants;

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
public class AssKpiConfigMgrImpl implements IAssKpiConfigMgr {
 
	private IAssKpiConfigDao  assKpiConfigDao;
 	
	public IAssKpiConfigDao getAssKpiConfigDao() {
		return this.assKpiConfigDao;
	}
 	
	public void setAssKpiConfigDao(IAssKpiConfigDao assKpiConfigDao) {
		this.assKpiConfigDao = assKpiConfigDao;
	}
	
	public AssKpiConfig getAssKpiConfig(final String id) {
    	return assKpiConfigDao.getAssKpiConfig(id);
    }
    
    public void saveAssKpiConfig(AssKpiConfig assKpiConfig) {
    	boolean isNew = (null == assKpiConfig.getId() || "".equals(assKpiConfig.getId()));
    	if (isNew) {
    		// 生成新节点Id
    		String nodeId = getUsableNodeId(assKpiConfig.getParentNodeId());
    		assKpiConfig.setNodeId(nodeId);
    		assKpiConfig.setLeaf(AssKpiConfigConstants.NODE_LEAF);
    		// 保存新节点
    		assKpiConfigDao.saveAssKpiConfig(assKpiConfig);
    		
    		// 如果父节点不是根结点则设置父节点为非叶子节点
    		if (!AssKpiConfigConstants.TREE_ROOT_ID.equals(assKpiConfig.getParentNodeId())) {
    			updateNodeLeaf(assKpiConfig.getParentNodeId(), AssKpiConfigConstants.NODE_NOTLEAF);
    		}
    	} else {
    		assKpiConfigDao.saveAssKpiConfig(assKpiConfig);
    	}
    }
    
	public AssKpiConfig getAssKpiConfigByNodeId(final String nodeId) {
		return assKpiConfigDao.getAssKpiConfigByNodeId(nodeId);
	}
	
	public void removeAssKpiConfigByNodeId(final String nodeId) {
		AssKpiConfig assKpiConfig = assKpiConfigDao.getAssKpiConfigByNodeId(nodeId);
		// 获得父节点Id
		String parentNodeId = assKpiConfig.getParentNodeId();
		assKpiConfigDao.removeAssKpiConfigByNodeId(nodeId);
		
		// 删除节点后若父节点下已经没有其他子节点则将父节点设置为叶子节点
		if (!AssKpiConfigConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			if (!isHasNextLevel(parentNodeId)) {
				updateNodeLeaf(parentNodeId, AssKpiConfigConstants.NODE_LEAF);
			}
		}
	}
	
	public List getNextLevelAssKpiConfigs(String parentNodeId) {
		return assKpiConfigDao.getNextLevelAssKpiConfigs(parentNodeId);
	}
	
	public String getUsableNodeId(String parentNodeId) {
		return assKpiConfigDao.getUsableNodeId(parentNodeId, parentNodeId.length()
				+ AssKpiConfigConstants.NODEID_BETWEEN_LENGTH);
	}
	
	public boolean isHasNextLevel(String parentNodeId) {
		boolean flag = false;
		List list = assKpiConfigDao.getNextLevelAssKpiConfigs(parentNodeId);
		if (list.iterator().hasNext()) {
			flag = true;
		}
		return flag;
	}
	
	public void updateNodeLeaf(String nodeId, String leaf) {
		AssKpiConfig assKpiConfig = assKpiConfigDao.getAssKpiConfigByNodeId(nodeId);
		assKpiConfig.setLeaf(leaf);
		assKpiConfigDao.saveAssKpiConfig(assKpiConfig);
	}
	
	public List getChildNodes(String parentNodeId) {
		return assKpiConfigDao.getChildNodes(parentNodeId);
	}
	
	public List getAssKpiConfigs( final String whereStr ) {
		return assKpiConfigDao.getAssKpiConfigs(whereStr);
	}	
}