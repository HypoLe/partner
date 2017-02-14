package com.boco.eoms.partner.tranEva.mgr;

import java.util.Map;

import com.boco.eoms.partner.tranEva.dao.ITranEvaAuditInfoDao;
import com.boco.eoms.partner.tranEva.model.TranEvaAuditInfo;

public interface ITranEvaAuditInfoMgr {

	public ITranEvaAuditInfoDao getTranEvaAuditInfoDao();
	
	public void setTranEvaAuditInfoDao(ITranEvaAuditInfoDao auditInfoDao);
	
	public TranEvaAuditInfo getTranEvaAuditInfo(String id);
	
	public void saveTranEvaAuditInfo(TranEvaAuditInfo tranEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
