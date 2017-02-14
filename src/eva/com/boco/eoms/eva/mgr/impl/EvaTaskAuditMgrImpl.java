package com.boco.eoms.eva.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.eva.dao.IEvaTaskAuditDao;
import com.boco.eoms.eva.mgr.IEvaTaskAuditMgr;
import com.boco.eoms.eva.model.EvaTaskAudit;

public class EvaTaskAuditMgrImpl implements IEvaTaskAuditMgr{

	 
	

	public List getEvaTaskAuditByTaskId(String taskId,String partner) {
		return pnrEvaTaskAuditDao.getEvaTaskAuditByTaskId(taskId,partner);
	}

	public IEvaTaskAuditDao pnrEvaTaskAuditDao ;

	public IEvaTaskAuditDao getEvaTaskAuditDao() {
		return pnrEvaTaskAuditDao;
	}

	public void setEvaTaskAuditDao(IEvaTaskAuditDao pnrEvaTaskAuditDao) {
		this.pnrEvaTaskAuditDao = pnrEvaTaskAuditDao;
	}

	public List getEvaTaskAudit(String taskId, String auditTime,
			String auditCycle,String partner) {
		// TODO Auto-generated method stub
		return pnrEvaTaskAuditDao.getEvaTaskAudit(taskId, auditTime, auditCycle,partner);
	}

	public EvaTaskAudit getEvaTaskAudit(String id) {
		// TODO Auto-generated method stub
		return pnrEvaTaskAuditDao.getEvaTaskAudit(id);
	}

	public Map getEvaTaskAuditByOrgType(Integer curPage, Integer pageSize,
			String whereStr) {
		// TODO Auto-generated method stub
		return pnrEvaTaskAuditDao.getEvaTaskAuditByOrgType(curPage, pageSize, whereStr);
	}

	public void saveEvaTaskAudit(EvaTaskAudit taskAudit) {
		pnrEvaTaskAuditDao.saveEvaTaskAudit(taskAudit);
	}
	
	
}
