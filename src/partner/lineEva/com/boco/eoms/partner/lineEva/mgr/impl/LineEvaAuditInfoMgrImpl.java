package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.Map;


import com.boco.eoms.partner.lineEva.dao.ILineEvaAuditInfoDao;
import com.boco.eoms.partner.lineEva.model.LineEvaAuditInfo;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaAuditInfoMgr;

public class LineEvaAuditInfoMgrImpl implements ILineEvaAuditInfoMgr{

	private ILineEvaAuditInfoDao auditInfoDao;
	
	public ILineEvaAuditInfoDao getLineEvaAuditInfoDao(){
		return auditInfoDao;
	}

	public void setLineEvaAuditInfoDao(ILineEvaAuditInfoDao auditInfoDao) {
		this.auditInfoDao = auditInfoDao;
	}

	public LineEvaAuditInfo getLineEvaAuditInfo(String id) {
		return auditInfoDao.getLineEvaAuditInfo(id);
	}
	
	public void saveLineEvaAuditInfo(LineEvaAuditInfo lineEvaAuditInfo) {
		auditInfoDao.saveLineEvaAuditInfo(lineEvaAuditInfo);
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return auditInfoDao.getAuditInfoByTempateId(curPage,pageSize,whereStr);
	}
}
