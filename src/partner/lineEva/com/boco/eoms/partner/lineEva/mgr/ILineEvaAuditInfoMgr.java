package com.boco.eoms.partner.lineEva.mgr;

import java.util.Map;

import com.boco.eoms.partner.lineEva.dao.ILineEvaAuditInfoDao;
import com.boco.eoms.partner.lineEva.model.LineEvaAuditInfo;

public interface ILineEvaAuditInfoMgr {

	public ILineEvaAuditInfoDao getLineEvaAuditInfoDao();
	
	public void setLineEvaAuditInfoDao(ILineEvaAuditInfoDao auditInfoDao);
	
	public LineEvaAuditInfo getLineEvaAuditInfo(String id);
	
	public void saveLineEvaAuditInfo(LineEvaAuditInfo lineEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
