package com.boco.eoms.partner.eva.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.eva.dao.IPnrEvaAuditInfoDao;
import com.boco.eoms.partner.eva.model.PnrEvaAuditInfo;

public interface IPnrEvaAuditInfoMgr {

	public IPnrEvaAuditInfoDao getPnrEvaAuditInfoDao();
	
	public void setPnrEvaAuditInfoDao(IPnrEvaAuditInfoDao auditInfoDao);
	
	public PnrEvaAuditInfo getPnrEvaAuditInfo(String id);
	
	public List getPnrEvaAudit(final String templateId);
	
	public void savePnrEvaAuditInfo(PnrEvaAuditInfo evaAuditInfo);
	
	public Map getAuditInfoByCondition(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
