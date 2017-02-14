package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PartnerEvaluation;
import com.boco.eoms.partner.baseinfo.mgr.PartnerEvaluationMgr;
import com.boco.eoms.partner.baseinfo.dao.PartnerEvaluationDao;

/**
 * <p>
 * Title:合作伙伴服务评价
 * </p>
 * <p>
 * Description:合作伙伴服务评价
 * </p>
 * <p>
 * Tue Apr 27 16:50:24 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class PartnerEvaluationMgrImpl implements PartnerEvaluationMgr {
 
	private PartnerEvaluationDao  partnerEvaluationDao;
 	
	public PartnerEvaluationDao getPartnerEvaluationDao() {
		return this.partnerEvaluationDao;
	}
 	
	public void setPartnerEvaluationDao(PartnerEvaluationDao partnerEvaluationDao) {
		this.partnerEvaluationDao = partnerEvaluationDao;
	}
 	
    public List getPartnerEvaluations() {
    	return partnerEvaluationDao.getPartnerEvaluations();
    }
    
    public PartnerEvaluation getPartnerEvaluation(final String id) {
    	return partnerEvaluationDao.getPartnerEvaluation(id);
    }
    
    public void savePartnerEvaluation(PartnerEvaluation partnerEvaluation) {
    	partnerEvaluationDao.savePartnerEvaluation(partnerEvaluation);
    }
    
    public void removePartnerEvaluation(final String id) {
    	partnerEvaluationDao.removePartnerEvaluation(id);
    }
    
    public Map getPartnerEvaluations(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerEvaluationDao.getPartnerEvaluations(curPage, pageSize, whereStr);
	}
	
}