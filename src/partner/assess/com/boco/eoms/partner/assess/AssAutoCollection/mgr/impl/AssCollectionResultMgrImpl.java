package com.boco.eoms.partner.assess.AssAutoCollection.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAutoCollection.dao.AssCollectionResultDao;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionResultMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionResult;

/**
 * <p>
 * Title:采集结果
 * </p>
 * <p>
 * Description:采集结果
 * </p>
 * <p>
 * Thu Apr 07 14:49:20 CST 2011
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class AssCollectionResultMgrImpl implements AssCollectionResultMgr {
 
	private AssCollectionResultDao  assCollectionResultDao;
	
	public AssCollectionResultDao getAssCollectionResultDao() {
		return this.assCollectionResultDao;
	}
 	
	public void setAssCollectionResultDao(AssCollectionResultDao assCollectionResultDao) {
		this.assCollectionResultDao = assCollectionResultDao;
	}
 	
    public List getAssCollectionResults() {
    	return assCollectionResultDao.getAssCollectionResults();
    }
    
    public AssCollectionResult getAssCollectionResult(final String id) {
    	return assCollectionResultDao.getAssCollectionResult(id);
    }
    
    public void saveAssCollectionResult(AssCollectionResult assCollectionResult) {
    	assCollectionResultDao.saveAssCollectionResult(assCollectionResult);
    }
    
    public void removeAssCollectionResult(final String id) {
    	assCollectionResultDao.removeAssCollectionResult(id);
    }
    
    public Map getAssCollectionResults(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return assCollectionResultDao.getAssCollectionResults(curPage, pageSize, whereStr);
	}
	
	public List getAssCollectionResults( final String whereStr ) {
		return assCollectionResultDao.getAssCollectionResults(whereStr);
	}
}