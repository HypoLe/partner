package com.boco.eoms.parter.baseinfo.fee.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeAuditDao;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeAuditMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeAudit;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;

/**
 * <p>
 * Title:费用管理审核
 * </p>
 * <p>
 * Description:费用管理审核
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PartnerFeeAuditMgrImpl implements PartnerFeeAuditMgr {
 
	private PartnerFeeAuditDao  partnerFeeAuditDao;
 	
	public PartnerFeeAuditDao getPartnerFeeAuditDao() {
		return this.partnerFeeAuditDao;
	}
	public void setPartnerFeeAuditDao(PartnerFeeAuditDao partnerFeeAuditDao) {
		this.partnerFeeAuditDao = partnerFeeAuditDao;
	}
 	
    public List getPartnerFeeAudits(final String feeId,final String type) {
    	return partnerFeeAuditDao.getPartnerFeeAudits(feeId,type);
    }
    public Map getPartnerFeeUnAudits(final Integer curPage,final Integer pageSize,final String userId,final String deptId,final String type){
    	return partnerFeeAuditDao.getPartnerFeeUnAudits(curPage,pageSize,userId,deptId,type);
    }
	public PartnerFeeAudit getPartnerFeeAuditByState(final String feeId,final String type,final String state){
		return partnerFeeAuditDao.getPartnerFeeAuditByState(feeId,type,state);
	}
    public PartnerFeeAudit getPartnerFeeAudit(final String id) {
    	return partnerFeeAuditDao.getPartnerFeeAudit(id);
    }
    
    public void savePartnerFeeAudit(PartnerFeeAudit partnerFeeAudit) {
    	partnerFeeAuditDao.savePartnerFeeAudit(partnerFeeAudit);
    }
    
    public void savePartnerFeePlanAudit(PartnerFeePlan partnerFeePlan,String toOrgId,String toOrgType) {
    	PartnerFeeAudit partnerFeeAudit = new PartnerFeeAudit();
    	partnerFeeAudit.setFeeId(partnerFeePlan.getId());
    	partnerFeeAudit.setCreateTime(partnerFeePlan.getCreateDate());
    	partnerFeeAudit.setOperateId(partnerFeePlan.getCreateUser());
    	partnerFeeAudit.setOperateType("user");
    	partnerFeeAudit.setToOrgId(toOrgId);
    	partnerFeeAudit.setToOrgType(toOrgType);
    	partnerFeeAudit.setState(partnerFeePlan.getPayState());
    	partnerFeeAudit.setType("plan");
    	partnerFeeAuditDao.savePartnerFeeAudit(partnerFeeAudit);
    }
    
}