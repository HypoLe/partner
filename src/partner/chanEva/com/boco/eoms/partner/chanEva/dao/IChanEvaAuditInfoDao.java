package com.boco.eoms.partner.chanEva.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.chanEva.model.ChanEvaAuditInfo;

public interface IChanEvaAuditInfoDao extends Dao{

	public ChanEvaAuditInfo getChanEvaAuditInfo(String id);
	
	public void saveChanEvaAuditInfo(ChanEvaAuditInfo chanEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
