package com.boco.eoms.partner.assiEva.mgr;

import java.util.Map;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaAuditInfoDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaAuditInfo;

public interface IAssiEvaAuditInfoMgr {

	public IAssiEvaAuditInfoDao getAssiEvaAuditInfoDao();
	
	public void setAssiEvaAuditInfoDao(IAssiEvaAuditInfoDao auditInfoDao);
	
	public AssiEvaAuditInfo getAssiEvaAuditInfo(String id);
	
	public void saveAssiEvaAuditInfo(AssiEvaAuditInfo assiEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
