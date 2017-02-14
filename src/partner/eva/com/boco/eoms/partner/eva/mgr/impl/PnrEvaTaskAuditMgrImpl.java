package com.boco.eoms.partner.eva.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.eva.dao.IPnrEvaTaskAuditDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskAuditMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTaskAudit;

public class PnrEvaTaskAuditMgrImpl implements IPnrEvaTaskAuditMgr{

	 
	

	public List getPnrEvaTaskAuditByTaskId(String taskId,String partner) {
		return pnrEvaTaskAuditDao.getPnrEvaTaskAuditByTaskId(taskId,partner);
	}

	public IPnrEvaTaskAuditDao pnrEvaTaskAuditDao ;

	public IPnrEvaTaskAuditDao getPnrEvaTaskAuditDao() {
		return pnrEvaTaskAuditDao;
	}

	public void setPnrEvaTaskAuditDao(IPnrEvaTaskAuditDao pnrEvaTaskAuditDao) {
		this.pnrEvaTaskAuditDao = pnrEvaTaskAuditDao;
	}

	public List getPnrEvaTaskAudit(String taskId, String auditTime,
			String auditCycle,String partner) {
		// TODO Auto-generated method stub
		return pnrEvaTaskAuditDao.getPnrEvaTaskAudit(taskId, auditTime, auditCycle,partner);
	}

	public PnrEvaTaskAudit getPnrEvaTaskAudit(String id) {
		// TODO Auto-generated method stub
		return pnrEvaTaskAuditDao.getPnrEvaTaskAudit(id);
	}

	public Map getPnrEvaTaskAuditByOrgType(Integer curPage, Integer pageSize,
			String whereStr) {
		// TODO Auto-generated method stub
		return pnrEvaTaskAuditDao.getPnrEvaTaskAuditByOrgType(curPage, pageSize, whereStr);
	}

	public void savePnrEvaTaskAudit(PnrEvaTaskAudit taskAudit) {
		pnrEvaTaskAuditDao.savePnrEvaTaskAudit(taskAudit);
	}
	
	
}
