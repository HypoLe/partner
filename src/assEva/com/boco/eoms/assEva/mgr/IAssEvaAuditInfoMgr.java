package com.boco.eoms.assEva.mgr;

import java.util.Map;

import com.boco.eoms.assEva.dao.IAssEvaAuditInfoDao;
import com.boco.eoms.assEva.model.AssEvaAuditInfo;

public interface IAssEvaAuditInfoMgr {

	public IAssEvaAuditInfoDao getAssEvaAuditInfoDao();
	
	public void setAssEvaAuditInfoDao(IAssEvaAuditInfoDao auditInfoDao);
	
	public AssEvaAuditInfo getAssEvaAuditInfo(String id);
	
	public void saveAssEvaAuditInfo(AssEvaAuditInfo assEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
