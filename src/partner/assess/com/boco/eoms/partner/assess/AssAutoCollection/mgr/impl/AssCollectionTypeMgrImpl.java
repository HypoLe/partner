package com.boco.eoms.partner.assess.AssAutoCollection.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionTypeDao;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionTypeMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;

/**
 * <p>
 * Title:采集类型
 * </p>
 * <p>
 * Description:采集类型
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AssCollectionTypeMgrImpl implements AssCollectionTypeMgr {
 
	private AssCollectionTypeDao  assCollectionTypeDao;
 	
	public AssCollectionTypeDao getAssCollectionTypeDao() {
		return this.assCollectionTypeDao;
	}
 	
	public void setAssCollectionTypeDao(AssCollectionTypeDao assCollectionTypeDao) {
		this.assCollectionTypeDao = assCollectionTypeDao;
	}
 	
    public List getAssCollectionTypes() {
    	return assCollectionTypeDao.getAssCollectionTypes();
    }
    
    public AssCollectionType getAssCollectionType(final String id) {
    	return assCollectionTypeDao.getAssCollectionType(id);
    }
    
    public void saveAssCollectionType(AssCollectionType assCollectionType) {
    	assCollectionTypeDao.saveAssCollectionType(assCollectionType);
    }
    
    public void removeAssCollectionType(final String id) {
    	assCollectionTypeDao.removeAssCollectionType(id);
    }
    
    public Map getAssCollectionTypes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return assCollectionTypeDao.getAssCollectionTypes(curPage, pageSize, whereStr);
	}
    
	public AssCollectionType getAssCollectionTypeByNodeId(final String nodeId) {
		return assCollectionTypeDao.getAssCollectionTypeByNodeId(nodeId);
	} 	
}