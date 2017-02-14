package com.boco.eoms.partner.assess.AssAutoCollection.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionConfigDao;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionConfigMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;

/**
 * <p>
 * Title:采集配置
 * </p>
 * <p>
 * Description:采集配置
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssCollectionConfigMgrImpl implements AssCollectionConfigMgr {
 
	private AssCollectionConfigDao  assCollectionConfigDao;
 	
	public AssCollectionConfigDao getAssCollectionConfigDao() {
		return this.assCollectionConfigDao;
	}
 	
	public void setAssCollectionConfigDao(AssCollectionConfigDao assCollectionConfigDao) {
		this.assCollectionConfigDao = assCollectionConfigDao;
	}
 	
    public List getAssCollectionConfigs() {
    	return assCollectionConfigDao.getAssCollectionConfigs();
    }
    
    public AssCollectionConfig getAssCollectionConfig(final String id) {
    	return assCollectionConfigDao.getAssCollectionConfig(id);
    }
    
    public void saveAssCollectionConfig(AssCollectionConfig assCollectionConfig) {
    	assCollectionConfigDao.saveAssCollectionConfig(assCollectionConfig);
    }
    
    public void removeAssCollectionConfig(final String id) {
    	assCollectionConfigDao.removeAssCollectionConfig(id);
    }
    
    public Map getAssCollectionConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return assCollectionConfigDao.getAssCollectionConfigs(curPage, pageSize, whereStr);
	}
	
    public AssCollectionConfig getAssCollectionConfigByNodeId(final String nodeId) {
    	return assCollectionConfigDao.getAssCollectionConfigByNodeId(nodeId);
    }
}