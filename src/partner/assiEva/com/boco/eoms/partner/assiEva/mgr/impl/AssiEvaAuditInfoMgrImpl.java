package com.boco.eoms.partner.assiEva.mgr.impl;

import java.util.Map;


import com.boco.eoms.partner.assiEva.dao.IAssiEvaAuditInfoDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaAuditInfo;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaAuditInfoMgr;

public class AssiEvaAuditInfoMgrImpl implements IAssiEvaAuditInfoMgr{

	private IAssiEvaAuditInfoDao auditInfoDao;
	
	public IAssiEvaAuditInfoDao getAssiEvaAuditInfoDao(){
		return auditInfoDao;
	}

	public void setAssiEvaAuditInfoDao(IAssiEvaAuditInfoDao auditInfoDao) {
		this.auditInfoDao = auditInfoDao;
	}

	public AssiEvaAuditInfo getAssiEvaAuditInfo(String id) {
		return auditInfoDao.getAssiEvaAuditInfo(id);
	}
	
	public void saveAssiEvaAuditInfo(AssiEvaAuditInfo assiEvaAuditInfo) {
		auditInfoDao.saveAssiEvaAuditInfo(assiEvaAuditInfo);
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return auditInfoDao.getAuditInfoByTempateId(curPage,pageSize,whereStr);
	}
}
