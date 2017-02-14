package com.boco.eoms.partner.chanEva.mgr;

import java.util.Map;

import com.boco.eoms.partner.chanEva.dao.IChanEvaAuditInfoDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaAuditInfo;

public interface IChanEvaAuditInfoMgr {

	public IChanEvaAuditInfoDao getChanEvaAuditInfoDao();
	
	public void setChanEvaAuditInfoDao(IChanEvaAuditInfoDao auditInfoDao);
	
	public ChanEvaAuditInfo getChanEvaAuditInfo(String id);
	
	public void saveChanEvaAuditInfo(ChanEvaAuditInfo chanEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
