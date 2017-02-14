package com.boco.eoms.partner.tempTask.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskAuditDao;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskAuditMgr;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;


/**
 * <p>
 * Title:服务临时任务审核
 * </p>
 * <p>
 * Description:服务临时任务审核
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskAuditMgrImpl implements IPnrTempTaskAuditMgr {
 
	private IPnrTempTaskAuditDao  pnrTempTaskAuditDao;
 	
	public IPnrTempTaskAuditDao getPnrTempTaskAuditDao() {
		return this.pnrTempTaskAuditDao;
	}
	public void setPnrTempTaskAuditDao(IPnrTempTaskAuditDao pnrTempTaskAuditDao) {
		this.pnrTempTaskAuditDao = pnrTempTaskAuditDao;
	}
 	
    public List getPnrTempTaskAudits(final String tempTaskId) {
    	return pnrTempTaskAuditDao.getPnrTempTaskAudits(tempTaskId);
    }
    public Map getPnrTempTaskUnAudits(final Integer curPage,final Integer pageSize,final String userId,final String deptId){
    	return pnrTempTaskAuditDao.getPnrTempTaskUnAudits(curPage,pageSize,userId,deptId);
    }
	public PnrTempTaskAudit getPnrTempTaskAuditByState(final String tempTaskId,final String state){
		return pnrTempTaskAuditDao.getPnrTempTaskAuditByState(tempTaskId,state);
	}
    public PnrTempTaskAudit getPnrTempTaskAudit(final String id) {
    	return pnrTempTaskAuditDao.getPnrTempTaskAudit(id);
    }
    
    public void savePnrTempTaskAudit(PnrTempTaskAudit pnrTempTaskAudit) {
    	pnrTempTaskAuditDao.savePnrTempTaskAudit(pnrTempTaskAudit);
    }
    
    public void savePnrAgreeMainAudit(PnrTempTaskMain pnrTempTaskMain,String toOrgId,String toOrgType) {
    	PnrTempTaskAudit pnrTempTaskAudit = new PnrTempTaskAudit();
    	pnrTempTaskAudit.setTempTaskId(pnrTempTaskMain.getId());
    	pnrTempTaskAudit.setCreateTime(pnrTempTaskMain.getCreateTime());
    	pnrTempTaskAudit.setOperateId(pnrTempTaskMain.getCreateUser());
    	pnrTempTaskAudit.setOperateType("user");
    	pnrTempTaskAudit.setToOrgId(toOrgId);
    	pnrTempTaskAudit.setToOrgType(toOrgType);
    	pnrTempTaskAudit.setState(pnrTempTaskMain.getState());
    	pnrTempTaskAuditDao.savePnrTempTaskAudit(pnrTempTaskAudit);
    }
    
}