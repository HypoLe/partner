package com.boco.eoms.assEva.mgr.impl;

import java.util.Map;


import com.boco.eoms.assEva.dao.IAssEvaAuditInfoDao;
import com.boco.eoms.assEva.model.AssEvaAuditInfo;
import com.boco.eoms.assEva.mgr.IAssEvaAuditInfoMgr;

public class AssEvaAuditInfoMgrImpl implements IAssEvaAuditInfoMgr{

	private IAssEvaAuditInfoDao auditInfoDao;
	
	public IAssEvaAuditInfoDao getAssEvaAuditInfoDao(){
		return auditInfoDao;
	}

	public void setAssEvaAuditInfoDao(IAssEvaAuditInfoDao auditInfoDao) {
		this.auditInfoDao = auditInfoDao;
	}

	public AssEvaAuditInfo getAssEvaAuditInfo(String id) {
		return auditInfoDao.getAssEvaAuditInfo(id);
	}
	
	public void saveAssEvaAuditInfo(AssEvaAuditInfo assEvaAuditInfo) {
		auditInfoDao.saveAssEvaAuditInfo(assEvaAuditInfo);
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return auditInfoDao.getAuditInfoByTempateId(curPage,pageSize,whereStr);
	}
}
