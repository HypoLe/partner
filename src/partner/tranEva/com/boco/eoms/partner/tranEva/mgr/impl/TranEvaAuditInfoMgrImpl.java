package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.Map;


import com.boco.eoms.partner.tranEva.dao.ITranEvaAuditInfoDao;
import com.boco.eoms.partner.tranEva.model.TranEvaAuditInfo;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaAuditInfoMgr;

public class TranEvaAuditInfoMgrImpl implements ITranEvaAuditInfoMgr{

	private ITranEvaAuditInfoDao auditInfoDao;
	
	public ITranEvaAuditInfoDao getTranEvaAuditInfoDao(){
		return auditInfoDao;
	}

	public void setTranEvaAuditInfoDao(ITranEvaAuditInfoDao auditInfoDao) {
		this.auditInfoDao = auditInfoDao;
	}

	public TranEvaAuditInfo getTranEvaAuditInfo(String id) {
		return auditInfoDao.getTranEvaAuditInfo(id);
	}
	
	public void saveTranEvaAuditInfo(TranEvaAuditInfo tranEvaAuditInfo) {
		auditInfoDao.saveTranEvaAuditInfo(tranEvaAuditInfo);
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return auditInfoDao.getAuditInfoByTempateId(curPage,pageSize,whereStr);
	}
}
