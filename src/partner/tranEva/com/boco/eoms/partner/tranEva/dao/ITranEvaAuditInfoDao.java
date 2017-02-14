package com.boco.eoms.partner.tranEva.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.tranEva.model.TranEvaAuditInfo;

public interface ITranEvaAuditInfoDao extends Dao{

	public TranEvaAuditInfo getTranEvaAuditInfo(String id);
	
	public void saveTranEvaAuditInfo(TranEvaAuditInfo tranEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
