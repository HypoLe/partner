package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.dao.BackEstimateDao;
import com.boco.eoms.partner.deviceAssess.mgr.BackEstimateMgr;
import com.boco.eoms.partner.deviceAssess.model.BackEstimate;

/**
 * <p>
 * Title:后评估指标体系
 * </p>
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p>
 * Thu Oct 14 10:55:23 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class BackEstimateMgrImpl implements BackEstimateMgr {
 
	private BackEstimateDao  backEstimateDao;
 	
	public BackEstimateDao getBackEstimateDao() {
		return this.backEstimateDao;
	}
 	
	public void setBackEstimateDao(BackEstimateDao backEstimateDao) {
		this.backEstimateDao = backEstimateDao;
	}
 	
    public List getBackEstimates() {
    	return backEstimateDao.getBackEstimates();
    }
    
    public BackEstimate getBackEstimate(final String id) {
    	return backEstimateDao.getBackEstimate(id);
    }
    
    public void saveBackEstimate(BackEstimate backEstimate) {
    	backEstimateDao.saveBackEstimate(backEstimate);
    }
    
    public void removeBackEstimate(final String id) {
    	backEstimateDao.removeBackEstimate(id);
    }
    
    public Map getBackEstimates(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return backEstimateDao.getBackEstimates(curPage, pageSize, whereStr);
	}
    
	public List getBackEstimatesList( final String whereStr ) {
		return backEstimateDao.getBackEstimatesList(whereStr);
	} 	
}